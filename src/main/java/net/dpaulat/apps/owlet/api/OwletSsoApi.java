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

package net.dpaulat.apps.owlet.api;

import net.dpaulat.apps.firebase.api.FirebaseAuthenticationApi;
import net.dpaulat.apps.firebase.json.VerifyPasswordRequest;
import net.dpaulat.apps.firebase.json.VerifyPasswordResponse;
import net.dpaulat.apps.owlet.OwletApiConfig;
import net.dpaulat.apps.owlet.OwletRegion;
import net.dpaulat.apps.owlet.json.OwletAuthorization;
import net.dpaulat.apps.owletnotifier.ConfigProperties;
import net.dpaulat.apps.rest.api.RestApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;

import javax.validation.constraints.NotNull;

/**
 * Used for single sign-on to the Owlet API
 */
@Service
public class OwletSsoApi extends RestApi {

    private static final Logger log = LoggerFactory.getLogger(OwletSsoApi.class);

    private static final String authUri = "/";

    protected OwletSsoApi(@NotNull ApplicationContext context,
                          @NotNull ConfigProperties configProperties) {
        super(context, OwletApiConfig.getConfig(configProperties.getOwlet().getRegion()).getUrlMini());
    }

    public String authenticate(String jwt) {
        String miniToken = null;

        OwletAuthorization auth = get(authUri, httpHeaders -> httpHeaders.add("Authorization", jwt),
                                      OwletAuthorization.class);

        if (auth != null) {
            log.info("Owlet SSO authentication successful");
            log.debug(auth.toString());

            miniToken = auth.getMiniToken();
        } else {
            log.warn("Owlet SSO authentication unsuccessful");
        }

        return miniToken;
    }
}
