package net.dpaulat.apps.owlet;

import net.dpaulat.apps.ayla.api.AylaUsersApi;
import net.dpaulat.apps.ayla.json.AylaSignInResponse;
import net.dpaulat.apps.owlet.json.OwletApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OwletApi {

    private static final Logger log = LoggerFactory.getLogger(OwletApi.class);

    private AylaUsersApi aylaUsersApi;
    private AylaSignInResponse aylaSignInResponse;

    private OwletApplication owletApplication;

    public OwletApi() {
        this.aylaUsersApi = new AylaUsersApi();
        this.aylaSignInResponse = null;
        this.owletApplication = new OwletApplication();
    }

    public void signIn(String email, String password) {
        aylaSignInResponse = aylaUsersApi.signIn(email, password, new OwletApplication());
    }

    public void refreshToken() {
        if (aylaSignInResponse != null) {
            aylaSignInResponse = aylaUsersApi.refreshToken(aylaSignInResponse.getRefreshToken());
        }
    }

    public boolean isSignedIn() {
        return aylaSignInResponse != null;
    }
}
