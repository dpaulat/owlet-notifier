package net.dpaulat.apps.alexa.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AccessTokenResponse {

    @JsonProperty(value = "access_token")
    @NotNull
    private String accessToken;

    @JsonProperty(value = "token_type")
    @NotNull
    private String tokenType;

    @JsonProperty(value = "expires_in")
    @NotNull
    private Long expiresIn;

    @JsonProperty(value = "refresh_token")
    @NotNull
    private String refreshToken;

    public AccessTokenResponse() {
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public Long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    @Override
    public String toString() {
        return "AccessTokenResponse{" +
               "accessToken='" + accessToken + '\'' +
               ", tokenType='" + tokenType + '\'' +
               ", expiresIn=" + expiresIn +
               ", refreshToken='" + refreshToken + '\'' +
               '}';
    }
}
