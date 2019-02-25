/*
 * Copyright 2019 Dan Paulat
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

import net.dpaulat.apps.ayla.json.AylaApplication;
import net.dpaulat.apps.ayla.json.AylaAuthorizationByEmail;
import net.dpaulat.apps.ayla.json.AylaUserLoginInput;
import net.dpaulat.apps.ayla.json.AylaUserRefresh;
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

    private static final String baseUrl = "https://user-field.aylanetworks.com/users";
    private static final String signInUri = "/sign_in";
    private static final String refreshTokenUri = "/refresh_token";

    public AylaUsersApi(@NotNull ApplicationContext context) {
        super(context, baseUrl);
    }

    public AylaAuthorizationByEmail signIn(String email, String password, AylaApplication application) {
        AylaUserLoginInput signInRequest = new AylaUserLoginInput(email, password, application);
        AylaAuthorizationByEmail signInResponse;

        log.info("Signing in user {}", email);
        log.debug(signInRequest.toString());

        signInResponse = post(signInUri, httpHeaders -> {
        }, BodyInserters.fromObject(signInRequest), AylaAuthorizationByEmail.class);

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
        }, BodyInserters.fromObject(refreshTokenRequest), AylaAuthorizationByEmail.class);

        if (signInResponse != null) {
            log.info("Refresh successful, expiration in {} seconds", signInResponse.getExpiresIn());
            log.debug(signInResponse.toString());
        } else {
            log.info("Refresh unsuccessful");
        }

        return signInResponse;
    }
}
