package net.dpaulat.apps.ayla.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AylaSignInRequest {

    private AylaUser user;

    public AylaSignInRequest() {
    }

    public static AylaSignInRequest create(String email, String password, AylaApplication application) {
        AylaSignInRequest signInRequest = new AylaSignInRequest();
        signInRequest.setUser(AylaUser.create(email, password, application));
        return signInRequest;
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
