package net.dpaulat.apps.alexa.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RefreshTokenRequest {

    @JsonProperty(value = "grant_type")
    @NotNull
    private String grantType;

    @JsonProperty(value = "refresh_token")
    @NotNull
    private String refreshToken;

    public RefreshTokenRequest() {
    }

    public String getGrantType() {
        return grantType;
    }

    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    @Override
    public String toString() {
        return "RefreshTokenRequest{" +
               "grantType='" + grantType + '\'' +
               ", refreshToken='" + refreshToken + '\'' +
               '}';
    }
}
