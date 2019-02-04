package net.dpaulat.apps.alexa.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AccessTokenRequest {

    @JsonProperty(value = "grant_type")
    @NotNull
    private String grantType;

    @NotNull
    private String code;

    @JsonProperty(value = "redirect_uri")
    @NotNull
    private String redirectUri;

    @JsonProperty(value = "client_id")
    @NotNull
    private String clientId;

    @JsonProperty(value = "client_secret")
    @NotNull
    private String clientSecret;

    public AccessTokenRequest() {
    }

    public String getGrantType() {
        return grantType;
    }

    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    @Override
    public String toString() {
        return "AccessTokenRequest{" +
               "grantType='" + grantType + '\'' +
               ", code='" + code + '\'' +
               ", redirectUri='" + redirectUri + '\'' +
               ", clientId='" + clientId + '\'' +
               ", clientSecret='" + clientSecret + '\'' +
               '}';
    }
}
