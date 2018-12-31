package net.dpaulat.apps.owlet.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OwletSignInResponse {

    @JsonProperty(value = "access_token")
    private String accessToken;
    @JsonProperty(value = "refresh_token")
    private String refreshToken;
    @JsonProperty(value = "expires_in")
    private Integer expiresIn;
    private String role;
    @JsonProperty(value = "role_tags")
    private String[] roleTags;

    public OwletSignInResponse() {
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

    public String[] getRoleTags() {
        return roleTags;
    }

    public void setRoleTags(String[] roleTags) {
        this.roleTags = roleTags;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("OwletSignInResponse{access_token=");
        str.append(accessToken);
        str.append(", refresh_token=");
        str.append(refreshToken);
        str.append(", expires_in=");
        str.append(expiresIn);
        str.append(", role=");
        str.append(role);
        str.append(", role_tags=[");
        for (int i = 0; i < roleTags.length; i++) {
            str.append(roleTags[i]);
            if (i < roleTags.length - 1) {
                str.append(", ");
            }
        }
        str.append("]}");
        return str.toString();
    }
}
