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

package net.dpaulat.apps.rest.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ClientHttpRequest;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Consumer;
import java.util.function.Function;

public class RestApi {

    private static final Logger log = LoggerFactory.getLogger(RestApi.class);

    private final ApplicationContext context;
    private final WebClient webClient;

    protected RestApi(ApplicationContext context, String baseUrl) {
        this.context = context;
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
            handleWebClientResponseException(ex);
        }

        return response;
    }

    protected <T> T post(String uri,
                         Consumer<HttpHeaders> headersConsumer,
                         BodyInserter<?, ? super ClientHttpRequest> body,
                         Class<T> responseType) {
        return post(uri,
                headersConsumer,
                body,
                clientResponse -> Mono.error(new WebClientResponseException(
                        clientResponse.rawStatusCode(),
                        clientResponse.statusCode().getReasonPhrase(),
                        clientResponse.headers().asHttpHeaders(),
                        null, null)),
                this::handleWebClientResponseException,
                responseType);
    }

    protected <T> T post(String uri,
                         Consumer<HttpHeaders> headersConsumer,
                         BodyInserter<?, ? super ClientHttpRequest> body,
                         Function<ClientResponse, Mono<? extends Throwable>> errorFunction,
                         Consumer<WebClientResponseException> errorExceptionHandler,
                         Class<T> responseType) {

        Mono<T> webResponse = webClient
                .post()
                .uri(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .headers(headersConsumer)
                .body(body)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::isError, errorFunction)
                .bodyToMono(responseType);

        T response = null;

        try {
            response = webResponse.block();
        } catch (WebClientResponseException ex) {
            errorExceptionHandler.accept(ex);
        }

        return response;
    }

    private void handleWebClientResponseException(WebClientResponseException ex) {
        log.error(ex.getMessage());

        if (ex.getStatusCode() == HttpStatus.UNAUTHORIZED) {
            log.error("Fatal client authorization error, exiting");
            SpringApplication.exit(context, () -> -1);
        }
    }
}
