package net.dpaulat.apps.ayla.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AylaUserRefreshToken {

    @JsonProperty(value = "refresh_token")
    private String refreshToken;

    public AylaUserRefreshToken() {
    }

    public AylaUserRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    @Override
    public String toString() {
        return "AylaUserRefreshToken{" +
                "refreshToken='" + refreshToken + '\'' +
                '}';
    }
}
