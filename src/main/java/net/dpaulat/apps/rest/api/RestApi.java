package net.dpaulat.apps.rest.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ClientHttpRequest;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Consumer;

public class RestApi {

    private static final Logger log = LoggerFactory.getLogger(RestApi.class);

    private final WebClient webClient;

    protected RestApi(String baseUrl) {
        this.webClient = WebClient.create(baseUrl);
    }

    protected <T> Iterable<T> getIterable(String uri, Consumer<HttpHeaders> headersConsumer, Class<T> responseType) {

        Flux<T> webResponse = webClient
                .get()
                .uri(uri)
                .headers(headersConsumer)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::isError, clientResponse ->
                        Mono.error(new WebClientResponseException(clientResponse.rawStatusCode(),
                                clientResponse.statusCode().getReasonPhrase(), null, null, null)))
                .bodyToFlux(responseType);

        Iterable<T> response = null;

        try {
            response = webResponse.toIterable();
        } catch (WebClientResponseException ex) {
            log.error(ex.getMessage());
        }

        return response;
    }

    protected <T> T post(String uri, Consumer<HttpHeaders> headersConsumer,
                         BodyInserter<?, ? super ClientHttpRequest> body, Class<T> responseType) {

        Mono<T> webResponse = webClient
                .post()
                .uri(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .headers(headersConsumer)
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
}
