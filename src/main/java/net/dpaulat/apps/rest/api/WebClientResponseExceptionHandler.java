package net.dpaulat.apps.rest.api;

import org.slf4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.reactive.function.client.WebClientResponseException;

public interface WebClientResponseExceptionHandler {

    void handle(WebClientResponseException ex, ApplicationContext context, Logger log);
}
