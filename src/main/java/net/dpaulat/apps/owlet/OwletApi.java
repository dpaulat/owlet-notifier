package net.dpaulat.apps.owlet;

import net.dpaulat.apps.ayla.api.AylaDeviceApi;
import net.dpaulat.apps.ayla.api.AylaUsersApi;
import net.dpaulat.apps.ayla.json.AylaAuthorizationByEmail;
import net.dpaulat.apps.ayla.json.AylaDevProperty;
import net.dpaulat.apps.ayla.json.AylaDevice;
import net.dpaulat.apps.owlet.json.OwletApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OwletApi {

    private static final Logger log = LoggerFactory.getLogger(OwletApi.class);

    private AylaDeviceApi aylaDeviceApi;
    private AylaUsersApi aylaUsersApi;

    private OwletApplication owletApplication;

    private AylaAuthorizationByEmail authorization;
    private List<AylaDevice> deviceList;
    private Map<String, Map<String, String>> deviceMap;

    public OwletApi() {
        this.aylaDeviceApi = new AylaDeviceApi();
        this.aylaUsersApi = new AylaUsersApi();
        this.authorization = null;
        this.deviceList = null;
        this.deviceMap = new HashMap<>();
        this.owletApplication = new OwletApplication();
    }

    public void signIn(String email, String password) {
        authorization = aylaUsersApi.signIn(email, password, owletApplication);
    }

    public void refreshToken() {
        if (authorization != null) {
            authorization = aylaUsersApi.refreshToken(authorization);
        }
    }

    public List<AylaDevice> retrieveDevices() {
        if (authorization != null) {
            deviceList = aylaDeviceApi.retrieveDevices(authorization);
        }

        return deviceList;
    }

    public void updateProperties(AylaDevice device, IOwletPropertyUpdated onUpdate) {
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
                if (onUpdate != null) {
                    onUpdate.callback(property.getName(), propertyMap.get(property.getName()), property.getValue());
                }
                propertyMap.put(property.getName(), property.getValue());
            }
        }
    }

    public String getPropertyValue(AylaDevice device, Properties propertyName) {
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

    public boolean isSignedIn() {
        return authorization != null;
    }

    public enum Properties {
        ALRTS_DISABLED("Disable Alerts"),
        ALRT_SNS_BLE("Alert Sense Ble"),
        ALRT_SNS_YLW("Alert Sense Yellow"),
        APP_ACTIVE("App Active"),
        AVERAGE_DATA("Average Data"),
        BABY_NAME("Baby"),
        BASE_STATION_ON("Base Station On"),
        BATT_LEVEL("Battery Level (%)"),
        BIRTHDATE("Birthdate"),
        BLE_MAC_ID("Sock BLE Id"),
        BLE_RSSI("BLE RSSI"),
        CHARGE_STATUS("Charge Status"),
        CRIT_BATT_ALRT("Crit. Battery Alert"),
        CRIT_OX_ALRT("Crit. Oxygen Alert"),
        DEVICE_PING("Device Ping"),
        DISABLE_LOGGED_DATA("Disable Logged Data"),
        ELEVATION("Elevation"),
        GENDER("Gender"),
        HEART_RATE("Heart Rate"),
        HIGH_HR_ALRT("High HR Alert"),
        LATITUDE("Latitude"),
        LIVE_DATA_STREAM("Live Data Stream"),
        LOCAL_BLE_MAC_ID("Base BLE Mac Id"),
        LOGGED_DATA_CACHE("Logged Data Cache"),
        LONGITUDE("Longitude"),
        LOW_BATT_ALRT("Low Battery Alert"),
        LOW_BATT_PRCNT("Low Batt. Percent"),
        LOW_HR_ALRT("Low HR Alert"),
        LOW_INTEG_READ("Low Integrity Read"),
        LOW_OX_ALRT("Low Oxygen Alert"),
        LOW_PA_ALRT("Low Pa Alert"),
        MOVEMENT("Baby Movement"),
        NURSERY_MODE("Nursery Mode"),
        oem_base_version("oem_base_version"),
        oem_sock_version("oem_sock_version"),
        ON_BOARDING("On Boarding"),
        OTA_ERROR("OTA Error"),
        OTA_STATUS("OTA Status"),
        OXYGEN_LEVEL("Oxygen Level"),
        PREMATURE("Premature"),
        SHARE_DATA("Share Data"),
        SOCK_CONNECTION("Sock Connection"),
        SOCK_DISCON_ALRT("Sock Disconnect Alert"),
        SOCK_DIS_APP_PREF("Sock Dis. App Pref."),
        SOCK_DIS_NEST_PREF("Sock Dis. Nest Pref."),
        SOCK_OFF("Sock Off"),
        SOCK_REC_PLACED("Sock Recently Placed"),
        UNKNOWN("Unknown property");

        private final String displayName;

        Properties(String displayName) {
            this.displayName = displayName;
        }

        public static Properties toEnum(String name) {
            for (Properties myEnum : Properties.values()) {
                if (myEnum.name().equals(name)) {
                    return myEnum;
                }
            }
            return UNKNOWN;
        }

        public String getDisplayName() {
            return displayName;
        }
    }
}
