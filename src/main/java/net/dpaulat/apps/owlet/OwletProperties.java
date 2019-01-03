package net.dpaulat.apps.owlet;

public enum OwletProperties {
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
    SOCK_REC_PLACED("Sock Recently Placed");

    private final String displayName;

    OwletProperties(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
