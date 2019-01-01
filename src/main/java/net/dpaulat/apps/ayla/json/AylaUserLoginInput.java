package net.dpaulat.apps.ayla.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AylaUserLoginInput {

    private AylaUserIdentifyByEmail user;

    public AylaUserLoginInput() {
    }

    public AylaUserLoginInput(String email, String password, AylaApplication application) {
        this.user = new AylaUserIdentifyByEmail(email, password, application);
    }

    public AylaUserIdentifyByEmail getUser() {
        return user;
    }

    public void setUser(AylaUserIdentifyByEmail user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "AylaUserLoginInput{" +
                "user=" + user +
                '}';
    }
}
