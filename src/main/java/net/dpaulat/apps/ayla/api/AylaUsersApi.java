/*
 * Copyright 2019-2021 Dan Paulat
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.dpaulat.apps.ayla.api;

import net.dpaulat.apps.ayla.json.*;
import net.dpaulat.apps.owlet.OwletApiConfig;
import net.dpaulat.apps.owletnotifier.ConfigProperties;
import net.dpaulat.apps.rest.api.RestApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;

import javax.validation.constraints.NotNull;

/**
 * API to interact with the Ayla User Service
 *
 * @link https://developer.aylanetworks.com/apibrowser/swaggers/UserService
 */
@Service
public class AylaUsersApi extends RestApi {

    private static final Logger log = LoggerFactory.getLogger(AylaUsersApi.class);

    private static final String signInUri = "/users/sign_in";
    private static final String tokenSignInUri = "/api/v1/token_sign_in";
    private static final String refreshTokenUri = "/users/refresh_token";
    private static final String provider = "owl_id";

    public AylaUsersApi(@NotNull ApplicationContext context, @NotNull ConfigProperties configProperties) {
        super(context, OwletApiConfig.getConfig(configProperties.getOwlet().getRegion()).getUrlUser());
    }

    public AylaAuthorizationByEmail signIn(String email, String password, AylaApplication application) {
        AylaUserLoginInput signInRequest = new AylaUserLoginInput(email, password, application);
        AylaAuthorizationByEmail signInResponse;

        log.info("Signing in user {}", email);
        log.debug(signInRequest.toString());

        signInResponse = post(signInUri, httpHeaders -> {
        }, BodyInserters.fromValue(signInRequest), AylaAuthorizationByEmail.class);

        if (signInResponse != null) {
            log.info("Sign in successful, expiration in {} seconds", signInResponse.getExpiresIn());
            log.debug(signInResponse.toString());
        } else {
            log.info("Sign in unsuccessful");
        }

        return signInResponse;
    }

    public AylaAuthorizationByEmail signIn(String token, AylaApplication application) {
        AylaTokenLoginInput signInRequest = new AylaTokenLoginInput(application.getAppId(),
                                                                    application.getAppSecret(), provider, token);
        AylaAuthorizationByEmail signInResponse;

        log.info("Signing in user with token");
        log.debug(signInRequest.toString());

        signInResponse = post(tokenSignInUri, httpHeaders -> {
        }, BodyInserters.fromValue(signInRequest), AylaAuthorizationByEmail.class);

        if (signInResponse != null) {
            log.info("Sign in successful, expiration in {} seconds", signInResponse.getExpiresIn());
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

        signInResponse = post(refreshTokenUri, httpHeaders -> {
        }, BodyInserters.fromValue(refreshTokenRequest), AylaAuthorizationByEmail.class);

        if (signInResponse != null) {
            log.info("Refresh successful, expiration in {} seconds", signInResponse.getExpiresIn());
            log.debug(signInResponse.toString());
        } else {
            log.info("Refresh unsuccessful");
        }

        return signInResponse;
    }
}
