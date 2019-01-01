package net.dpaulat.apps.ayla.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AylaUser {

    private String email;
    private String password;
    private AylaApplication application;

    public AylaUser() {
    }

    public static AylaUser create(String email, String password, AylaApplication application) {
        AylaUser user = new AylaUser();
        user.setEmail(email);
        user.setPassword(password);
        user.setApplication(application);
        return user;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public AylaApplication getApplication() {
        return application;
    }

    public void setApplication(AylaApplication application) {
        this.application = application;
    }

    @Override
    public String toString() {
        return "AylaUser{" +
                "email='" + email + '\'' +
                ", password='***'" +
                ", application=" + application +
                '}';
    }
}
