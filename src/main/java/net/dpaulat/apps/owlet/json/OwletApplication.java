package net.dpaulat.apps.owlet.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OwletApplication {

    @JsonProperty(value = "app_id")
    private String appId;
    @JsonProperty(value = "app_secret")
    private String appSecret;

    public OwletApplication() {
    }

    public static OwletApplication create() {
        OwletApplication application = new OwletApplication();
        application.setAppId("OWL-id");
        application.setAppSecret("OWL-4163742");
        return application;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    @Override
    public String toString() {
        return "OwletApplication{app_id=" + appId + ", app_secret=" + appSecret + "}";
    }
}
