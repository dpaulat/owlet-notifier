package net.dpaulat.apps.owletnotifier.alexa.handler;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.interfaces.messaging.MessageReceivedRequest;
import net.dpaulat.apps.owletnotifier.ConfigProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Optional;

@Service
public class MessageReceivedRequestHandler implements com.amazon.ask.dispatcher.request.handler.impl.MessageReceivedRequestHandler {

    private static final Logger log = LoggerFactory.getLogger(MessageReceivedRequestHandler.class);

    private final @NotNull ConfigProperties config;

    public MessageReceivedRequestHandler(@NotNull ConfigProperties config) {
        this.config = config;
    }

    @Override
    public boolean canHandle(HandlerInput input, MessageReceivedRequest messageReceivedRequest) {
        return true;
    }

    @Override
    public Optional<Response> handle(HandlerInput input, MessageReceivedRequest messageReceivedRequest) {
        log.info(input.getRequestEnvelope().toString());
        log.info(messageReceivedRequest.toString());

        return Optional.empty();
    }
}
