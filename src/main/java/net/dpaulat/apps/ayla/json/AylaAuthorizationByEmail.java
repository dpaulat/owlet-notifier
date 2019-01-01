package net.dpaulat.apps.ayla.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AylaAuthorizationByEmail {

    @JsonProperty(value = "access_token")
    private String accessToken;
    @JsonProperty(value = "refresh_token")
    private String refreshToken;
    @JsonProperty(value = "expires_in")
    private Integer expiresIn;
    private String role;
    @JsonProperty(value = "role_tags")
    private AylaRoleTag[] roleTags;

    public AylaAuthorizationByEmail() {
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Integer getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Integer expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public AylaRoleTag[] getRoleTags() {
        return roleTags;
    }

    public void setRoleTags(AylaRoleTag[] roleTags) {
        this.roleTags = roleTags;
    }

    @Override
    public String toString() {
        return "AylaAuthorizationByEmail{" +
                "accessToken='" + accessToken + '\'' +
                ", refreshToken='" + refreshToken + '\'' +
                ", expiresIn=" + expiresIn +
                ", role='" + role + '\'' +
                ", roleTags=" + Arrays.toString(roleTags) +
                '}';
    }
}
