package net.dpaulat.apps.alexa.api;

import net.dpaulat.apps.alexa.json.*;
import net.dpaulat.apps.rest.api.RestApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
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

    protected AlexaApi(@NotNull ApplicationContext context) {
        super(context, baseUrl);
    }

    public void requestAuthorization(String clientId, String scope, String redirectUri, String state) {

        // https://developer.amazon.com/docs/login-with-amazon/authorization-code-grant.html
        AuthorizationRequest authorizationRequest = new AuthorizationRequest();
        authorizationRequest.setClientId(clientId);
        authorizationRequest.setScope(scope);
        authorizationRequest.setResponseType("code");
        authorizationRequest.setRedirectUri(redirectUri);
        authorizationRequest.setState(state);

        try {
            AuthorizationResponse authorizationResponse = post("https://www.amazon.com/ap/oa",
                    httpHeaders -> {
                    },
                    BodyInserters.fromObject(authorizationRequest),
                    this::errorResponse,
                    this::handleWebClientResponseException,
                    AuthorizationResponse.class);

            log.warn(authorizationResponse.toString());
        } catch (WebClientResponseException ex) {
            log.info(ex.getMessage());
        }
    }

    public void requestAccessToken(String code, String redirectUri, String clientId, String clientSecret) {

        // https://developer.amazon.com/docs/login-with-amazon/authorization-code-grant.html
        AccessTokenRequest accessTokenRequest = new AccessTokenRequest();
        accessTokenRequest.setGrantType("Authorization_code");
        accessTokenRequest.setCode(code);
        accessTokenRequest.setRedirectUri(redirectUri);
        accessTokenRequest.setClientId(clientId);
        accessTokenRequest.setClientSecret(clientSecret);

        try {
            AccessTokenResponse accessTokenResponse = post("https://api.amazon.com/auth/o2/token",
                    httpHeaders -> {
                    },
                    BodyInserters.fromObject(accessTokenRequest),
                    this::errorResponse,
                    this::handleWebClientResponseException,
                    AccessTokenResponse.class);

            log.warn(accessTokenResponse.toString());
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
