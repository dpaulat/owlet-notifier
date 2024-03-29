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

package net.dpaulat.apps.alexa.api;

import net.dpaulat.apps.alexa.json.AccessTokenRequest;
import net.dpaulat.apps.alexa.json.AccessTokenResponse;
import net.dpaulat.apps.alexa.json.ErrorResponse;
import net.dpaulat.apps.alexa.json.SkillMessageRequest;
import net.dpaulat.apps.owletnotifier.ConfigProperties;
import net.dpaulat.apps.rest.api.RestApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import javax.validation.constraints.NotNull;

@Component
public class AlexaApi extends RestApi {

    private static final Logger log = LoggerFactory.getLogger(AlexaApi.class);

    private static final String baseUrl = "";

    private final @NotNull ConfigProperties config;

    private AccessTokenResponse accessTokenResponse = null;

    protected AlexaApi(@NotNull ApplicationContext context, @NotNull ConfigProperties config) {
        super(context, baseUrl);

        this.config = config;
    }

    public AccessTokenResponse requestAccessToken() {

        // https://developer.amazon.com/docs/smapi/configure-an-application-or-service-to-send-messages-to-your-skill.html#request-format-to-obtain-access-token
        AccessTokenRequest accessTokenRequest = new AccessTokenRequest();
        accessTokenRequest.setGrantType("client_credentials");
        accessTokenRequest.setClientId(config.getAlexa().getClientId());
        accessTokenRequest.setClientSecret(config.getAlexa().getClientSecret());
        accessTokenRequest.setScope("alexa:skill_messaging");

        try {
            accessTokenResponse = post("https://api.amazon.com/auth/o2/token",
                    httpHeaders -> {
                    },
                    BodyInserters.fromValue(accessTokenRequest),
                    this::errorResponse,
                    this::handleWebClientResponseException,
                    AccessTokenResponse.class);

            log.info("Alexa sign in successful, expiration in {} seconds", accessTokenResponse.getExpiresIn());
            log.debug(accessTokenResponse.toString());
        } catch (WebClientResponseException ex) {
            accessTokenResponse = null;
            log.info("Alexa sign in unsuccessful");
            log.info(ex.getMessage());
        }

        return accessTokenResponse;
    }

    public boolean isAuthenticated() {
        return accessTokenResponse != null;
    }

    public void sendSkillMessage(String userId, ISkillMessage message) {
        SkillMessageRequest messageRequest = new SkillMessageRequest();
        messageRequest.getData().put("type", message.getClass().getSimpleName());
        messageRequest.getData().putAll(message.getData());

        if (!isAuthenticated()) {
            log.warn("Cannot send skill message, not authenticated");
            return;
        }

        try {
            post("https://api.amazonalexa.com/v1/skillmessages/users/" + userId,
                    httpHeaders ->
                            httpHeaders.add(HttpHeaders.AUTHORIZATION,
                                    "Bearer " + accessTokenResponse.getAccessToken()),
                    BodyInserters.fromValue(messageRequest),
                    this::errorResponse,
                    this::handleWebClientResponseException,
                    Object.class);
        } catch (WebClientResponseException ex) {
            log.info(ex.getMessage());
        }
    }

    private Mono<? extends Throwable> errorResponse(ClientResponse clientResponse) {
        ErrorResponse errorResponse = clientResponse.bodyToMono(ErrorResponse.class).block();
        log.error(errorResponse.toString());

        return Mono.error(new WebClientResponseException(
                clientResponse.rawStatusCode(),
                clientResponse.statusCode().getReasonPhrase(),
                clientResponse.headers().asHttpHeaders(),
                null, null));
    }

    private void handleWebClientResponseException(WebClientResponseException ex) {
        throw ex;
    }
}
