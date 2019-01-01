package net.dpaulat.apps.ayla.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AylaRefreshTokenRequest {

    private AylaUserRefresh user;

    public AylaRefreshTokenRequest() {
    }

    public AylaRefreshTokenRequest(String refreshToken) {
        this.user = new AylaUserRefresh(refreshToken);
    }

    public AylaUserRefresh getUser() {
        return user;
    }

    public void setUser(AylaUserRefresh user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "AylaRefreshTokenRequest{" +
                "user=" + user +
                '}';
    }
}
