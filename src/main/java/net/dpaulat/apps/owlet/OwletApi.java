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
    private final Map<String, Map<String, String>> deviceMap;
    private AylaAuthorizationByEmail authorization;
    private List<AylaDevice> deviceList;
    private boolean monitoringEnabled;

    public OwletApi(@NotNull AylaDeviceApi aylaDeviceApi, @NotNull AylaUsersApi aylaUsersApi) {
        this.aylaDeviceApi = aylaDeviceApi;
        this.aylaUsersApi = aylaUsersApi;
        this.owletApplication = new OwletApplication();
        this.deviceMap = new HashMap<>();
        this.authorization = null;
        this.deviceList = null;
        this.monitoringEnabled = false;
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

    public boolean isMonitoringEnabled() {
        return monitoringEnabled;
    }

    public void setMonitoringEnabled(boolean enabled) {
        log.info("Monitoring enabled: {}", enabled);
        this.monitoringEnabled = enabled;
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
}
