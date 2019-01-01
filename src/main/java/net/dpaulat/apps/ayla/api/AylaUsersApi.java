package net.dpaulat.apps.ayla.api;

import net.dpaulat.apps.ayla.json.AylaApplication;
import net.dpaulat.apps.ayla.json.AylaRefreshTokenRequest;
import net.dpaulat.apps.ayla.json.AylaSignInRequest;
import net.dpaulat.apps.ayla.json.AylaSignInResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ClientHttpRequest;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

public class AylaUsersApi {

    private static final Logger log = LoggerFactory.getLogger(AylaUsersApi.class);

    private static final String baseUrl = "https://user-field.aylanetworks.com/users/";
    private static final String signInUri = "sign_in";
    private static final String refreshTokenUri = "refresh_token";

    private final WebClient webClient;

    public AylaUsersApi() {
        this.webClient = WebClient.create(baseUrl);
    }

    private <T> T post(String uri, BodyInserter<?, ? super ClientHttpRequest> body, Class<T> responseType) {

        Mono<T> webResponse = webClient
                .post()
                .uri(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .body(body)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::isError, clientResponse ->
                        Mono.error(new WebClientResponseException(clientResponse.rawStatusCode(),
                                clientResponse.statusCode().getReasonPhrase(), null, null, null)))
                .bodyToMono(responseType);

        T response = null;

        try {
            response = webResponse.block();
        } catch (WebClientResponseException ex) {
            log.error(ex.getMessage());
        }

        return response;
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
