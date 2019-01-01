package net.dpaulat.apps.ayla.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AylaDevice {

    private Long id;
    @JsonProperty(value = "product_name")
    private String productName;
    private String model;
    private String dsn;
    private String oem;
    @JsonProperty(value = "oem_model")
    private String oemModel;
    @JsonProperty(value = "sw_version")
    private String swVersion;
    @JsonProperty(value = "user_id")
    private String userId;
    @JsonProperty(value = "user_uuid")
    private String userUuid;
    @JsonProperty(value = "template_id")
    private Long templateId;
    private String mac;
    @JsonProperty(value = "lan_ip")
    private String lanIp;
    private String ssid;
    @JsonProperty(value = "connected_at")
    private String connectedAt;
    private Long key;
    @JsonProperty(value = "product_class")
    private String productClass;
    @JsonProperty(value = "has_properties")
    private Boolean hasProperties;
    @JsonProperty(value = "lan_enabled")
    private Boolean lanEnabled;
    @JsonProperty(value = "enable_ssl")
    private Boolean enableSsl;
    @JsonProperty(value = "ans_enabled")
    private Boolean ansEnabled;
    @JsonProperty(value = "ans_server")
    private String ansServer;
    @JsonProperty(value = "log_enabled")
    private Boolean logEnabled;
    private Boolean registered;
    @JsonProperty(value = "connection_status")
    private String connectionStatus;
    @JsonProperty(value = "registration_type")
    private String registrationType;
    private Double lat;
    private Double lng;
    private String locality;
    private String homekit;
    @JsonProperty(value = "module_updated_at")
    private String moduleUpdatedAt;
    private Boolean registrable;
    private String regtoken;
    @JsonProperty(value = "setup_token")
    private String setupToken;
    private Boolean provisional;
    @JsonProperty(value = "device_type")
    private String deviceType;
    @JsonProperty(value = "activated_at")
    private String activatedAt;
    @JsonProperty(value = "created_at")
    private String createdAt;
    private AylaGrant grant;
    @JsonProperty(value = "gateway_type")
    private String gatewayType;

    public AylaDevice() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getDsn() {
        return dsn;
    }

    public void setDsn(String dsn) {
        this.dsn = dsn;
    }

    public String getOem() {
        return oem;
    }

    public void setOem(String oem) {
        this.oem = oem;
    }

    public String getOemModel() {
        return oemModel;
    }

    public void setOemModel(String oemModel) {
        this.oemModel = oemModel;
    }

    public String getSwVersion() {
        return swVersion;
    }

    public void setSwVersion(String swVersion) {
        this.swVersion = swVersion;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(String userUuid) {
        this.userUuid = userUuid;
    }

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getLanIp() {
        return lanIp;
    }

    public void setLanIp(String lanIp) {
        this.lanIp = lanIp;
    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public String getConnectedAt() {
        return connectedAt;
    }

    public void setConnectedAt(String connectedAt) {
        this.connectedAt = connectedAt;
    }

    public Long getKey() {
        return key;
    }

    public void setKey(Long key) {
        this.key = key;
    }

    public String getProductClass() {
        return productClass;
    }

    public void setProductClass(String productClass) {
        this.productClass = productClass;
    }

    public Boolean getHasProperties() {
        return hasProperties;
    }

    public void setHasProperties(Boolean hasProperties) {
        this.hasProperties = hasProperties;
    }

    public Boolean getLanEnabled() {
        return lanEnabled;
    }

    public void setLanEnabled(Boolean lanEnabled) {
        this.lanEnabled = lanEnabled;
    }

    public Boolean getEnableSsl() {
        return enableSsl;
    }

    public void setEnableSsl(Boolean enableSsl) {
        this.enableSsl = enableSsl;
    }

    public Boolean getAnsEnabled() {
        return ansEnabled;
    }

    public void setAnsEnabled(Boolean ansEnabled) {
        this.ansEnabled = ansEnabled;
    }

    public String getAnsServer() {
        return ansServer;
    }

    public void setAnsServer(String ansServer) {
        this.ansServer = ansServer;
    }

    public Boolean getLogEnabled() {
        return logEnabled;
    }

    public void setLogEnabled(Boolean logEnabled) {
        this.logEnabled = logEnabled;
    }

    public Boolean getRegistered() {
        return registered;
    }

    public void setRegistered(Boolean registered) {
        this.registered = registered;
    }

    public String getConnectionStatus() {
        return connectionStatus;
    }

    public void setConnectionStatus(String connectionStatus) {
        this.connectionStatus = connectionStatus;
    }

    public String getRegistrationType() {
        return registrationType;
    }

    public void setRegistrationType(String registrationType) {
        this.registrationType = registrationType;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getHomekit() {
        return homekit;
    }

    public void setHomekit(String homekit) {
        this.homekit = homekit;
    }

    public String getModuleUpdatedAt() {
        return moduleUpdatedAt;
    }

    public void setModuleUpdatedAt(String moduleUpdatedAt) {
        this.moduleUpdatedAt = moduleUpdatedAt;
    }

    public Boolean getRegistrable() {
        return registrable;
    }

    public void setRegistrable(Boolean registrable) {
        this.registrable = registrable;
    }

    public String getRegtoken() {
        return regtoken;
    }

    public void setRegtoken(String regtoken) {
        this.regtoken = regtoken;
    }

    public String getSetupToken() {
        return setupToken;
    }

    public void setSetupToken(String setupToken) {
        this.setupToken = setupToken;
    }

    public Boolean getProvisional() {
        return provisional;
    }

    public void setProvisional(Boolean provisional) {
        this.provisional = provisional;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getActivatedAt() {
        return activatedAt;
    }

    public void setActivatedAt(String activatedAt) {
        this.activatedAt = activatedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public AylaGrant getGrant() {
        return grant;
    }

    public void setGrant(AylaGrant grant) {
        this.grant = grant;
    }

    public String getGatewayType() {
        return gatewayType;
    }

    public void setGatewayType(String gatewayType) {
        this.gatewayType = gatewayType;
    }

    @Override
    public String toString() {
        return "AylaDevice{" +
                "id=" + id +
                ", productName='" + productName + '\'' +
                ", model='" + model + '\'' +
                ", dsn='" + dsn + '\'' +
                ", oem='" + oem + '\'' +
                ", oemModel='" + oemModel + '\'' +
                ", swVersion='" + swVersion + '\'' +
                ", userId='" + userId + '\'' +
                ", userUuid='" + userUuid + '\'' +
                ", templateId=" + templateId +
                ", mac='" + mac + '\'' +
                ", lanIp='" + lanIp + '\'' +
                ", ssid='" + ssid + '\'' +
                ", connectedAt='" + connectedAt + '\'' +
                ", key=" + key +
                ", productClass='" + productClass + '\'' +
                ", hasProperties=" + hasProperties +
                ", lanEnabled=" + lanEnabled +
                ", enableSsl=" + enableSsl +
                ", ansEnabled=" + ansEnabled +
                ", ansServer='" + ansServer + '\'' +
                ", logEnabled=" + logEnabled +
                ", registered=" + registered +
                ", connectionStatus='" + connectionStatus + '\'' +
                ", registrationType='" + registrationType + '\'' +
                ", lat=" + lat +
                ", lng=" + lng +
                ", locality='" + locality + '\'' +
                ", homekit='" + homekit + '\'' +
                ", moduleUpdatedAt='" + moduleUpdatedAt + '\'' +
                ", registrable=" + registrable +
                ", regtoken='" + regtoken + '\'' +
                ", setupToken='" + setupToken + '\'' +
                ", provisional=" + provisional +
                ", deviceType='" + deviceType + '\'' +
                ", activatedAt='" + activatedAt + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", grant=" + grant +
                ", gatewayType='" + gatewayType + '\'' +
                '}';
    }
}
