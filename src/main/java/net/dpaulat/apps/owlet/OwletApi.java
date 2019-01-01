package net.dpaulat.apps.owlet;

import net.dpaulat.apps.ayla.api.AylaUsersApi;
import net.dpaulat.apps.ayla.json.AylaSignInResponse;
import net.dpaulat.apps.owlet.json.OwletApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OwletApi {

    private static final Logger log = LoggerFactory.getLogger(OwletApi.class);

    private AylaUsersApi aylaUsersApi;
    private AylaSignInResponse signInResponse;

    public OwletApi() {
        this.aylaUsersApi = new AylaUsersApi();
        this.signInResponse = null;
    }

    public void signIn(String email, String password) {
        signInResponse = aylaUsersApi.signIn(email, password, OwletApplication.create());
    }

    public boolean isSignedIn() {
        return signInResponse != null;
    }
}
