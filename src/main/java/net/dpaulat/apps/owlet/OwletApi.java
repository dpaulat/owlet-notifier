/*
 * Copyright 2019 Dan Paulat
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.dpaulat.apps.owlet;

import net.dpaulat.apps.ayla.api.AylaDeviceApi;
import net.dpaulat.apps.ayla.api.AylaUsersApi;
import net.dpaulat.apps.ayla.json.AylaApplication;
import net.dpaulat.apps.ayla.json.AylaAuthorizationByEmail;
import net.dpaulat.apps.ayla.json.AylaDevProperty;
import net.dpaulat.apps.ayla.json.AylaDevice;
import net.dpaulat.apps.firebase.api.FirebaseAuthenticationApi;
import net.dpaulat.apps.firebase.json.VerifyPasswordResponse;
import net.dpaulat.apps.owlet.api.OwletSsoApi;
import net.dpaulat.apps.owlet.json.OwletApplication;
import net.dpaulat.apps.owlet.json.OwletApplicationV2;
import net.dpaulat.apps.owlet.json.OwletApplicationV2Europe;
import net.dpaulat.apps.owletnotifier.ConfigProperties;
import net.dpaulat.apps.util.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OwletApi {

    private static final Logger log = LoggerFactory.getLogger(OwletApi.class);

    private static final String androidPackage = "com.owletcare.owletcare";
    private static final String androidCert = "2A3BC26DB0B8B0792DBE28E6FFDC2598F9B12B74";

    private final AylaDeviceApi aylaDeviceApi;
    private final AylaUsersApi aylaUsersApi;
    private final FirebaseAuthenticationApi firebaseAuthenticationApi;
    private final OwletSsoApi owletSsoApi;

    private final OwletRegion owletRegion;
    private final OwletApiConfig owletApiConfig;
    private final Map<String, Map<String, String>> deviceMap;
    private final Map<String, Boolean> monitoringEnabled;
    private AylaAuthorizationByEmail authorization;
    private List<AylaDevice> deviceList;

    public OwletApi(@NotNull AylaDeviceApi aylaDeviceApi,
                    @NotNull AylaUsersApi aylaUsersApi,
                    @NotNull FirebaseAuthenticationApi firebaseAuthenticationApi,
                    @NotNull OwletSsoApi owletSsoApi,
                    @NotNull ConfigProperties configProperties) {
        this.aylaDeviceApi = aylaDeviceApi;
        this.aylaUsersApi = aylaUsersApi;
        this.firebaseAuthenticationApi = firebaseAuthenticationApi;
        this.owletSsoApi = owletSsoApi;
        this.owletRegion = configProperties.getOwlet().getRegion();
        this.owletApiConfig = OwletApiConfig.getConfig(owletRegion);
        this.deviceMap = new HashMap<>();
        this.monitoringEnabled = new HashMap<>();
        this.authorization = null;
        this.deviceList = null;
    }

    @SuppressWarnings("unchecked")
    private static <T> T asT(Boolean b) {
        return (T) b;
    }

    @SuppressWarnings("unchecked")
    private static <T> T asT(Integer i) {
        return (T) i;
    }

    @SuppressWarnings("unchecked")
    private static <T> T asT(String s) {
        return (T) s;
    }

    private static <T> T parse(String from, Class<T> type) {
        T value = null;

        if (type == Boolean.class) {
            value = asT(NumberUtils.tryParseBoolean(from));
        } else if (type == Integer.class) {
            value = asT(NumberUtils.tryParseInt(from));
        } else if (type == String.class) {
            value = asT(from);
        }

        return value;
    }

    public AylaAuthorizationByEmail signIn(String email, String password) {
        if (owletRegion == OwletRegion.Legacy) {
            authorization = aylaUsersApi.signIn(email, password, owletApiConfig.getApplication());
        } else {
            VerifyPasswordResponse firebaseResponse =
                    firebaseAuthenticationApi.getAuth(owletApiConfig.getApiKey(), email, password, androidPackage,
                                                      androidCert);
            String miniToken = null;

            if (firebaseResponse != null) {
                String jwt = firebaseResponse.getIdToken();
                miniToken = owletSsoApi.authenticate(jwt);
            }

            if (miniToken != null) {
                authorization = aylaUsersApi.signIn(miniToken, owletApiConfig.getApplication());
            }
        }

        return authorization;
    }

    public AylaAuthorizationByEmail refreshToken() {
        if (authorization != null) {
            authorization = aylaUsersApi.refreshToken(authorization);
        }

        return authorization;
    }

    public List<AylaDevice> getDevices() {
        return deviceList;
    }

    public List<AylaDevice> retrieveDevices() {
        if (authorization != null) {
            deviceList = aylaDeviceApi.retrieveDevices(authorization);

            // Device initializes to not monitored
            for (AylaDevice device : deviceList) {
                monitoringEnabled.put(device.getDsn(), false);
            }
        }

        return deviceList;
    }

    public void updateProperties(AylaDevice device) {
        List<AylaDevProperty> propertyList = null;

        if (authorization != null) {
            propertyList = aylaDeviceApi.retrieveDeviceProperties(authorization, device);
        }

        if (propertyList != null) {
            Map<String, String> propertyMap = getPropertyMap(device);

            for (AylaDevProperty property : propertyList) {
                propertyMap.put(property.getName(), property.getValue());
            }
        }
    }

    public <T> T getPropertyValue(AylaDevice device, OwletProperties propertyName, Class<T> type) {
        T value = null;
        Map<String, String> propertyMap = null;

        if (deviceMap.containsKey(device.getDsn())) {
            propertyMap = deviceMap.get(device.getDsn());
        }

        if (propertyMap != null) {
            value = parse(propertyMap.get(propertyName.name()), type);
        }

        return value;
    }

    public void setAppActive(AylaDevice device) {
        aylaDeviceApi.createDatapoint(authorization, device, OwletProperties.APP_ACTIVE, "1");
    }

    public boolean isSignedIn() {
        return authorization != null;
    }

    public boolean isMonitoringEnabled(AylaDevice device) {
        return monitoringEnabled.get(device.getDsn());
    }

    public boolean isAnyMonitoringEnabled() {
        if (deviceList != null) {
            for (AylaDevice device : deviceList) {
                if (isMonitoringEnabled(device)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void setMonitoringEnabled(AylaDevice device, boolean enabled) {
        log.info("Monitoring enabled for {}: {}", device.getDsn(), enabled);
        monitoringEnabled.put(device.getDsn(), enabled);
    }

    public void setAllMonitoringEnabled(boolean enabled) {
        if (deviceList != null) {
            for (AylaDevice device : deviceList) {
                setMonitoringEnabled(device, enabled);
            }
        }
    }

    public boolean isSockReady(AylaDevice device) {
        final Boolean baseStationOn = getPropertyValue(device, OwletProperties.BASE_STATION_ON, Boolean.class);
        final Integer chargeStatus = getPropertyValue(device, OwletProperties.CHARGE_STATUS, Integer.class);
        final Boolean movement = getPropertyValue(device, OwletProperties.MOVEMENT, Boolean.class);
        final Boolean sockRecentlyPlaced = getPropertyValue(device, OwletProperties.SOCK_REC_PLACED, Boolean.class);

        return ((baseStationOn != null && baseStationOn) &&
                (chargeStatus != null && chargeStatus == 0) &&
                (movement != null && !movement) &&
                (sockRecentlyPlaced != null && !sockRecentlyPlaced));
    }

    private Map<String, String> getPropertyMap(AylaDevice device) {
        Map<String, String> propertyMap;
        if (deviceMap.containsKey(device.getDsn())) {
            propertyMap = deviceMap.get(device.getDsn());
        } else {
            propertyMap = new HashMap<>();
            deviceMap.put(device.getDsn(), propertyMap);
        }
        return propertyMap;
    }
}
