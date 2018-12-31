package net.dpaulat.apps.owlet.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OwletUser {

    private String email;
    private String password;
    private OwletApplication application;

    public OwletUser() {
    }

    public static OwletUser create(String email, String password) {
        OwletUser user = new OwletUser();
        user.setEmail(email);
        user.setPassword(password);
        user.setApplication(OwletApplication.create());
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

    public OwletApplication getApplication() {
        return application;
    }

    public void setApplication(OwletApplication application) {
        this.application = application;
    }

    @Override
    public String toString() {
        return "OwletApplication{email=" + email + ", password=xxxxxxxx, application=" + application + "}";
    }
}
