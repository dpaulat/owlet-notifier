package net.dpaulat.apps.ayla.api;

import net.dpaulat.apps.ayla.json.AylaApplication;
import net.dpaulat.apps.ayla.json.AylaSignInRequest;
import net.dpaulat.apps.ayla.json.AylaSignInResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

public class AylaUsersApi {

    private static final Logger log = LoggerFactory.getLogger(AylaUsersApi.class);

    private static final String baseUrl = "https://user-field.aylanetworks.com/users/";
    private static final String signInUri = "sign_in";

    private final WebClient webClient;

    public AylaUsersApi() {
        this.webClient = WebClient.create(baseUrl);
    }

    public AylaSignInResponse signIn(String email, String password, AylaApplication application) {
        AylaSignInRequest signInRequest = AylaSignInRequest.create(email, password, application);
        AylaSignInResponse signInResponse = null;

        log.info("Signing in user {}", email);
        log.debug(signInRequest.toString());

        Mono<AylaSignInResponse> webResponse = webClient
                .post()
                .uri(signInUri)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(signInRequest))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::isError, clientResponse ->
                        Mono.error(new WebClientResponseException(clientResponse.rawStatusCode(),
                                clientResponse.statusCode().getReasonPhrase(), null, null, null)))
                .bodyToMono(AylaSignInResponse.class);

        try {
            signInResponse = webResponse.block();
            log.info("Sign in successful");
            log.debug(signInResponse.toString());
        } catch (WebClientResponseException ex) {
            log.info(ex.getMessage());
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }

        return signInResponse;
    }
}
