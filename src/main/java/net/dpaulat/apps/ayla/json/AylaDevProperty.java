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

package net.dpaulat.apps.ayla.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AylaDevProperty {

    private String type;
    private String name;
    @JsonProperty(value = "base_type")
    private String baseType;
    @JsonProperty(value = "read_only")
    private Boolean readOnly;
    private String direction;
    private String scope;
    @JsonProperty(value = "data_updated_at")
    private String dataUpdatedAt;
    private Long key;
    @JsonProperty(value = "device_key")
    private Long deviceKey;
    @JsonProperty(value = "product_name")
    private String productName;

    @JsonProperty(value = "track_only_changes")
    private Boolean trackOnlyChanges;
    @JsonProperty(value = "display_name")
    private String displayName;
    @JsonProperty(value = "host_sw_version")
    private Boolean hostSwVersion;
    @JsonProperty(value = "time_series")
    private Boolean timeSeries;
    private Boolean derived;
    @JsonProperty(value = "app_type")
    private String appType;
    private String recipe;
    private String value;
    @JsonProperty(value = "denied_roles")
    private String[] deniedRoles;
    @JsonProperty(value = "ack_enabled")
    private Boolean ackEnabled;
    @JsonProperty(value = "retention_days")
    private Long retentionDays;
    @JsonProperty(value = "ack_status")
    private Long ackStatus;
    @JsonProperty(value = "ack_message")
    private Long ackMessage;
    @JsonProperty(value = "acked_at")
    private String ackedAt;

    public AylaDevProperty() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBaseType() {
        return baseType;
    }

    public void setBaseType(String baseType) {
        this.baseType = baseType;
    }

    public Boolean getReadOnly() {
        return readOnly;
    }

    public void setReadOnly(Boolean readOnly) {
        this.readOnly = readOnly;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getDataUpdatedAt() {
        return dataUpdatedAt;
    }

    public void setDataUpdatedAt(String dataUpdatedAt) {
        this.dataUpdatedAt = dataUpdatedAt;
    }

    public Long getKey() {
        return key;
    }

    public void setKey(Long key) {
        this.key = key;
    }

    public Long getDeviceKey() {
        return deviceKey;
    }

    public void setDeviceKey(Long deviceKey) {
        this.deviceKey = deviceKey;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Boolean getTrackOnlyChanges() {
        return trackOnlyChanges;
    }

    public void setTrackOnlyChanges(Boolean trackOnlyChanges) {
        this.trackOnlyChanges = trackOnlyChanges;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Boolean getHostSwVersion() {
        return hostSwVersion;
    }

    public void setHostSwVersion(Boolean hostSwVersion) {
        this.hostSwVersion = hostSwVersion;
    }

    public Boolean getTimeSeries() {
        return timeSeries;
    }

    public void setTimeSeries(Boolean timeSeries) {
        this.timeSeries = timeSeries;
    }

    public Boolean getDerived() {
        return derived;
    }

    public void setDerived(Boolean derived) {
        this.derived = derived;
    }

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }

    public String getRecipe() {
        return recipe;
    }

    public void setRecipe(String recipe) {
        this.recipe = recipe;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String[] getDeniedRoles() {
        return deniedRoles;
    }

    public void setDeniedRoles(String[] deniedRoles) {
        this.deniedRoles = deniedRoles;
    }

    public Boolean getAckEnabled() {
        return ackEnabled;
    }

    public void setAckEnabled(Boolean ackEnabled) {
        this.ackEnabled = ackEnabled;
    }

    public Long getRetentionDays() {
        return retentionDays;
    }

    public void setRetentionDays(Long retentionDays) {
        this.retentionDays = retentionDays;
    }

    public Long getAckStatus() {
        return ackStatus;
    }

    public void setAckStatus(Long ackStatus) {
        this.ackStatus = ackStatus;
    }

    public Long getAckMessage() {
        return ackMessage;
    }

    public void setAckMessage(Long ackMessage) {
        this.ackMessage = ackMessage;
    }

    public String getAckedAt() {
        return ackedAt;
    }

    public void setAckedAt(String ackedAt) {
        this.ackedAt = ackedAt;
    }

    @Override
    public String toString() {
        return "AylaDevProperty{" +
               "type='" + type + '\'' +
               ", name='" + name + '\'' +
               ", baseType='" + baseType + '\'' +
               ", readOnly=" + readOnly +
               ", direction='" + direction + '\'' +
               ", scope='" + scope + '\'' +
               ", dataUpdatedAt='" + dataUpdatedAt + '\'' +
               ", key=" + key +
               ", deviceKey=" + deviceKey +
               ", productName='" + productName + '\'' +
               ", trackOnlyChanges=" + trackOnlyChanges +
               ", displayName='" + displayName + '\'' +
               ", hostSwVersion=" + hostSwVersion +
               ", timeSeries=" + timeSeries +
               ", derived=" + derived +
               ", appType='" + appType + '\'' +
               ", recipe='" + recipe + '\'' +
               ", value='" + value + '\'' +
               ", deniedRoles=" + Arrays.toString(deniedRoles) +
               ", ackEnabled=" + ackEnabled +
               ", retentionDays=" + retentionDays +
               ", ackStatus=" + ackStatus +
               ", ackMessage=" + ackMessage +
               ", ackedAt='" + ackedAt + '\'' +
               '}';
    }
}
