package net.dpaulat.apps.owlet.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OwletSignInRequest {

    private OwletUser user;

    public OwletSignInRequest() {
    }

    public static OwletSignInRequest create(String email, String password) {
        OwletSignInRequest signInRequest = new OwletSignInRequest();
        signInRequest.setUser(OwletUser.create(email, password));
        return signInRequest;
    }

    public OwletUser getUser() {
        return user;
    }

    public void setUser(OwletUser user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "OwletSignInRequest{user=" + user + "}";
    }
}
