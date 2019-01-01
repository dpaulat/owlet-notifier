package net.dpaulat.apps.ayla.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AylaSignInRequest {

    private AylaUser user;

    public AylaSignInRequest() {
    }

    public AylaSignInRequest(String email, String password, AylaApplication application) {
        this.user = new AylaUser(email, password, application);
    }

    public AylaUser getUser() {
        return user;
    }

    public void setUser(AylaUser user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "AylaSignInRequest{" +
                "user=" + user +
                '}';
    }
}
