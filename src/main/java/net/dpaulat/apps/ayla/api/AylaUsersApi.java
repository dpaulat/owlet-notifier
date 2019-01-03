package net.dpaulat.apps.ayla.api;

import net.dpaulat.apps.ayla.json.AylaApplication;
import net.dpaulat.apps.ayla.json.AylaAuthorizationByEmail;
import net.dpaulat.apps.ayla.json.AylaUserLoginInput;
import net.dpaulat.apps.ayla.json.AylaUserRefresh;
import net.dpaulat.apps.rest.api.RestApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;

/**
 * API to interact with the Ayla User Service
 *
 * @link https://developer.aylanetworks.com/apibrowser/swaggers/UserService
 */
@Service
public class AylaUsersApi extends RestApi {

    private static final Logger log = LoggerFactory.getLogger(AylaUsersApi.class);

    private static final String baseUrl = "https://user-field.aylanetworks.com/users";
    private static final String signInUri = "/sign_in";
    private static final String refreshTokenUri = "/refresh_token";

    public AylaUsersApi() {
        super(baseUrl);
    }

    public AylaAuthorizationByEmail signIn(String email, String password, AylaApplication application) {
        AylaUserLoginInput signInRequest = new AylaUserLoginInput(email, password, application);
        AylaAuthorizationByEmail signInResponse;

        log.info("Signing in user {}", email);
        log.debug(signInRequest.toString());

        signInResponse = post(signInUri, BodyInserters.fromObject(signInRequest), AylaAuthorizationByEmail.class);

        if (signInResponse != null) {
            log.info("Sign in successful");
            log.debug(signInResponse.toString());
        } else {
            log.info("Sign in unsuccessful");
        }

        return signInResponse;
    }

    public AylaAuthorizationByEmail refreshToken(AylaAuthorizationByEmail auth) {
        AylaUserRefresh refreshTokenRequest = new AylaUserRefresh(auth.getRefreshToken());
        AylaAuthorizationByEmail signInResponse;

        log.info("Refreshing access token");

        signInResponse = post(refreshTokenUri, BodyInserters.fromObject(refreshTokenRequest), AylaAuthorizationByEmail.class);

        if (signInResponse != null) {
            log.info("Refresh successful");
            log.debug(signInResponse.toString());
        } else {
            log.info("Refresh unsuccessful");
        }

        return signInResponse;
    }
}
