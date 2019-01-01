package net.dpaulat.apps.ayla.api;

import net.dpaulat.apps.ayla.json.AylaApplication;
import net.dpaulat.apps.ayla.json.AylaRefreshTokenRequest;
import net.dpaulat.apps.ayla.json.AylaSignInRequest;
import net.dpaulat.apps.ayla.json.AylaSignInResponse;
import net.dpaulat.apps.rest.api.RestApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.BodyInserters;

public class AylaUsersApi extends RestApi {

    private static final Logger log = LoggerFactory.getLogger(AylaUsersApi.class);

    private static final String baseUrl = "https://user-field.aylanetworks.com/users/";
    private static final String signInUri = "sign_in";
    private static final String refreshTokenUri = "refresh_token";

    public AylaUsersApi() {
        super(baseUrl);
    }

    public AylaSignInResponse signIn(String email, String password, AylaApplication application) {
        AylaSignInRequest signInRequest = new AylaSignInRequest(email, password, application);
        AylaSignInResponse signInResponse;

        log.info("Signing in user {}", email);
        log.debug(signInRequest.toString());

        signInResponse = post(signInUri, BodyInserters.fromObject(signInRequest), AylaSignInResponse.class);

        if (signInResponse != null) {
            log.info("Sign in successful");
            log.debug(signInResponse.toString());
        } else {
            log.info("Sign in unsuccessful");
        }

        return signInResponse;
    }

    public AylaSignInResponse refreshToken(String refreshToken) {
        AylaRefreshTokenRequest refreshTokenRequest = new AylaRefreshTokenRequest(refreshToken);
        AylaSignInResponse signInResponse;

        log.info("Refreshing access token");

        signInResponse = post(refreshTokenUri, BodyInserters.fromObject(refreshTokenRequest), AylaSignInResponse.class);

        if (signInResponse != null) {
            log.info("Refresh successful");
            log.debug(signInResponse.toString());
        } else {
            log.info("Refresh unsuccessful");
        }

        return signInResponse;
    }
}
