package net.dpaulat.apps.ayla.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AylaUserRefresh {

    private AylaUserRefreshToken user;

    public AylaUserRefresh() {
    }

    public AylaUserRefresh(String refreshToken) {
        this.user = new AylaUserRefreshToken(refreshToken);
    }

    public AylaUserRefreshToken getUser() {
        return user;
    }

    public void setUser(AylaUserRefreshToken user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "AylaUserRefresh{" +
                "user=" + user +
                '}';
    }
}
