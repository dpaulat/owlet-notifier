package net.dpaulat.apps.owlet;

import net.dpaulat.apps.ayla.api.AylaDeviceApi;
import net.dpaulat.apps.ayla.api.AylaUsersApi;
import net.dpaulat.apps.ayla.json.AylaAuthorizationByEmail;
import net.dpaulat.apps.owlet.json.OwletApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OwletApi {

    private static final Logger log = LoggerFactory.getLogger(OwletApi.class);

    private AylaDeviceApi aylaDeviceApi;
    private AylaUsersApi aylaUsersApi;
    private AylaAuthorizationByEmail authorization;

    private OwletApplication owletApplication;

    public OwletApi() {
        this.aylaDeviceApi = new AylaDeviceApi();
        this.aylaUsersApi = new AylaUsersApi();
        this.authorization = null;
        this.owletApplication = new OwletApplication();
    }

    public void signIn(String email, String password) {
        authorization = aylaUsersApi.signIn(email, password, owletApplication);
    }

    public void refreshToken() {
        if (authorization != null) {
            authorization = aylaUsersApi.refreshToken(authorization.getRefreshToken());
        }
    }

    public void retrieveDevices() {
        if (authorization != null) {
            aylaDeviceApi.retrieveDevices(authorization);
        }
    }

    public boolean isSignedIn() {
        return authorization != null;
    }
}
