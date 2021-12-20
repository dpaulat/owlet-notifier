/*
 * Copyright 2021 Dan Paulat
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

package net.dpaulat.apps.firebase.api;

import net.dpaulat.apps.firebase.json.VerifyPasswordRequest;
import net.dpaulat.apps.firebase.json.VerifyPasswordResponse;
import net.dpaulat.apps.rest.api.RestApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;

import javax.validation.constraints.NotNull;

/**
 * API to interact with the Firebase Authentication Service
 */
@Service
public class FirebaseAuthenticationApi extends RestApi {

    private static final Logger log = LoggerFactory.getLogger(FirebaseAuthenticationApi.class);

    private static final String baseUrl = "https://www.googleapis.com/identitytoolkit/v3/relyingparty";
    private static final String authUri = "/verifyPassword";

    protected FirebaseAuthenticationApi(@NotNull ApplicationContext context) {
        super(context, baseUrl);
    }

    public final VerifyPasswordResponse getAuth(String apiKey, String email, String password, String androidPackage,
                                                String androidCert) {
        VerifyPasswordRequest authRequest = new VerifyPasswordRequest();
        VerifyPasswordResponse authResponse;

        authRequest.setEmail(email);
        authRequest.setPassword(password);
        authRequest.setReturnSecureToken(true);

        authResponse = post(authUri + "?key=" + apiKey, httpHeaders -> {
            httpHeaders.add("X-Android-Package", androidPackage);
            httpHeaders.add("X-Android-Cert", androidCert);
        }, BodyInserters.fromValue(authRequest), VerifyPasswordResponse.class);

        if (authResponse != null) {
            log.info("Firebase authentication successful, expiration in {} seconds", authResponse.getExpiresIn());
            log.debug(authResponse.toString());
        } else {
            log.warn("Firebase authentication unsuccessful");
        }

        return authResponse;
    }
}
