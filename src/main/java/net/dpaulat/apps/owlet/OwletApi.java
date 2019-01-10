package net.dpaulat.apps.owlet;

import net.dpaulat.apps.ayla.api.AylaDeviceApi;
import net.dpaulat.apps.ayla.api.AylaUsersApi;
import net.dpaulat.apps.ayla.json.AylaAuthorizationByEmail;
import net.dpaulat.apps.ayla.json.AylaDevProperty;
import net.dpaulat.apps.ayla.json.AylaDevice;
import net.dpaulat.apps.owlet.json.OwletApplication;
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

    private final AylaDeviceApi aylaDeviceApi;
    private final AylaUsersApi aylaUsersApi;

    private final OwletApplication owletApplication;

    private AylaAuthorizationByEmail authorization;
    private List<AylaDevice> deviceList;
    private Map<String, Map<String, String>> deviceMap;
    private boolean monitoringEnabled;

    public OwletApi(@NotNull AylaDeviceApi aylaDeviceApi, @NotNull AylaUsersApi aylaUsersApi) {
        this.aylaDeviceApi = aylaDeviceApi;
        this.aylaUsersApi = aylaUsersApi;
        this.owletApplication = new OwletApplication();
        this.authorization = null;
        this.deviceList = null;
        this.deviceMap = new HashMap<>();
        this.monitoringEnabled = false;
    }

    public AylaAuthorizationByEmail signIn(String email, String password) {
        authorization = aylaUsersApi.signIn(email, password, owletApplication);
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
        }

        return deviceList;
    }

    public void updateProperties(AylaDevice device) {
        List<AylaDevProperty> propertyList = null;

        if (authorization != null) {
            propertyList = aylaDeviceApi.retrieveDeviceProperties(authorization, device);
        }

        if (propertyList != null) {
            Map<String, String> propertyMap;
            if (deviceMap.containsKey(device.getDsn())) {
                propertyMap = deviceMap.get(device.getDsn());
            } else {
                propertyMap = new HashMap<>();
                deviceMap.put(device.getDsn(), propertyMap);
            }

            for (AylaDevProperty property : propertyList) {
                propertyMap.put(property.getName(), property.getValue());
            }
        }
    }

    public String getPropertyValue(AylaDevice device, OwletProperties propertyName) {
        String value = null;
        Map<String, String> propertyMap = null;

        if (deviceMap.containsKey(device.getDsn())) {
            propertyMap = deviceMap.get(device.getDsn());
        }

        if (propertyMap != null) {
            value = propertyMap.get(propertyName.name());
        }

        return value;
    }

    public Integer getPropertyIntValue(AylaDevice device, OwletProperties propertyName) {
        String value = getPropertyValue(device, propertyName);
        Integer intValue = null;

        if (value != null) {
            try {
                intValue = NumberUtils.tryParseInt(value);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return intValue;
    }

    public void setAppActive(AylaDevice device) {
        aylaDeviceApi.createDatapoint(authorization, device, OwletProperties.APP_ACTIVE, "1");
    }

    public boolean isSignedIn() {
        return authorization != null;
    }

    public boolean isMonitoringEnabled() {
        return monitoringEnabled;
    }

    public void setMonitoringEnabled(boolean enabled) {
        log.info("Monitoring enabled: {}", enabled);
        this.monitoringEnabled = enabled;
    }

    public boolean isSockReady(AylaDevice device) {
        final Integer baseStationOn = getPropertyIntValue(device, OwletProperties.BASE_STATION_ON);
        final Integer chargeStatus = getPropertyIntValue(device, OwletProperties.CHARGE_STATUS);
        final Integer movement = getPropertyIntValue(device, OwletProperties.MOVEMENT);
        final Integer sockRecentlyPlaced = getPropertyIntValue(device, OwletProperties.SOCK_REC_PLACED);

        return (baseStationOn == 1 && chargeStatus == 0 && movement == 0 && sockRecentlyPlaced == 0);
    }
}
