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

public enum OwletProperties {
    AGE_MONTHS_OLD(Integer.class, "Age (Months)"),
    ALRTS_DISABLED(Boolean.class, "Disable Alerts"),
    ALRT_SNS_BLE(Integer.class, "Alert Sense Ble"),
    ALRT_SNS_YLW(Integer.class, "Alert Sense Yellow"),
    APP_ACTIVE(Boolean.class, "App Active"),
    AVERAGE_DATA(String.class, "Average Data"),
    BABY_NAME(String.class, "Baby"),
    BASE_STATION_ON(Boolean.class, "Base Station On"),
    BATT_LEVEL(Integer.class, "Battery Level (%)"),
    BIRTHDATE(String.class, "Birthdate"),
    BLE_MAC_ID(String.class, "Sock BLE Id"),
    BLE_RSSI(Integer.class, "BLE RSSI"),
    CHARGE_STATUS(Integer.class, "Charge Status"),
    CRIT_BATT_ALRT(Boolean.class, "Crit. Battery Alert"),
    CRIT_OX_ALRT(Boolean.class, "Crit. Oxygen Alert"),
    DEVICE_PING(Integer.class, "Device Ping"),
    DISABLE_LOGGED_DATA(Boolean.class, "Disable Logged Data"),
    ELEVATION(Integer.class, "Elevation"),
    GENDER(String.class, "Gender"),
    HEART_RATE(Integer.class, "Heart Rate"),
    HIGH_HR_ALRT(Boolean.class, "High HR Alert"),
    LATITUDE(Integer.class, "Latitude"),
    LIVE_DATA_STREAM(String.class, "Live Data Stream"),
    LOCAL_BLE_MAC_ID(String.class, "Base BLE Mac Id"),
    LOGGED_DATA_CACHE(String.class, "Logged Data Cache"),
    LONGITUDE(Integer.class, "Longitude"),
    LOW_BATT_ALRT(Boolean.class, "Low Battery Alert"),
    LOW_BATT_PRCNT(Integer.class, "Low Batt. Percent"),
    LOW_HR_ALRT(Boolean.class, "Low HR Alert"),
    LOW_INTEG_READ(Boolean.class, "Low Integrity Read"),
    LOW_OX_ALRT(Boolean.class, "Low Oxygen Alert"),
    LOW_PA_ALRT(Boolean.class, "Low Pa Alert"),
    MOVEMENT(Boolean.class, "Baby Movement"),
    NURSERY_MODE(Boolean.class, "Nursery Mode"),
    oem_base_version(String.class, "oem_base_version"),
    oem_sock_version(String.class, "oem_sock_version"),
    ON_BOARDING(Boolean.class, "On Boarding"),
    OTA_ERROR(Integer.class, "OTA Error"),
    OTA_STATUS(Integer.class, "OTA Status"),
    OXYGEN_LEVEL(Integer.class, "Oxygen Level"),
    PREMATURE(Boolean.class, "Premature"),
    SHARE_DATA(Boolean.class, "Share Data"),
    SOCK_CONNECTION(Boolean.class, "Sock Connection"),
    SOCK_DISCON_ALRT(Boolean.class, "Sock Disconnect Alert"),
    SOCK_DIS_APP_PREF(Boolean.class, "Sock Dis. App Pref."),
    SOCK_DIS_NEST_PREF(Boolean.class, "Sock Dis. Nest Pref."),
    SOCK_OFF(Boolean.class, "Sock Off"),
    SOCK_REC_PLACED(Boolean.class, "Sock Recently Placed");

    private final Class baseType;
    private final String displayName;

    OwletProperties(Class baseType, String displayName) {
        this.baseType = baseType;
        this.displayName = displayName;
    }

    public Class getBaseType() {
        return baseType;
    }

    public String getDisplayName() {
        return displayName;
    }
}
