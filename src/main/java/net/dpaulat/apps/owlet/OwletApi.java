package net.dpaulat.apps.owlet;

import net.dpaulat.apps.owlet.json.OwletSignInRequest;
import net.dpaulat.apps.owlet.json.OwletSignInResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

public class OwletApi {

    private static final Logger log = LoggerFactory.getLogger(OwletApi.class);

    private static final String signInURI = "https://user-field.aylanetworks.com/users/sign_in.json";

    private OwletSignInResponse signInResponse;

    public OwletApi() {
        this.signInResponse = null;
    }

    public void signIn(String email, String password) {
        OwletSignInRequest signInRequest = OwletSignInRequest.create(email, password);

        WebClient webClient = WebClient.create();
        Mono<OwletSignInResponse> response = webClient
                .post()
                .uri(signInURI)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(signInRequest))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::isError, clientResponse ->
                        Mono.error(new WebClientResponseException(clientResponse.rawStatusCode(),
                                clientResponse.statusCode().getReasonPhrase(), null, null, null)))
                .bodyToMono(OwletSignInResponse.class);

        try {
            signInResponse = response.block();
            log.info(signInResponse.toString());
        } catch (WebClientResponseException ex) {
            signInResponse = null;
            log.info(ex.getMessage());
        } catch (Exception ex) {
            signInResponse = null;
            log.error(ex.getMessage());
        }
    }

    public boolean isSignedIn() {
        return signInResponse != null;
    }
}
