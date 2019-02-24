package net.dpaulat.apps.owletnotifier.alexa.handler;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.impl.MessageReceivedRequestHandler;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.interfaces.messaging.MessageReceivedRequest;
import net.dpaulat.apps.alexa.api.ISkillMessage;
import net.dpaulat.apps.owletnotifier.alexa.data.ReminderRepository;
import net.dpaulat.apps.owletnotifier.alexa.message.NotificationMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Map;
import java.util.Optional;

@Service
public class NotificationMessageHandler implements MessageReceivedRequestHandler {

    private static final Logger log = LoggerFactory.getLogger(NotificationMessageHandler.class);

    private final ReminderRepository reminderRepository;

    public NotificationMessageHandler(@NotNull ReminderRepository reminderRepository) {
        this.reminderRepository = reminderRepository;
    }

    @Override
    public boolean canHandle(HandlerInput input, MessageReceivedRequest messageReceivedRequest) {
        Map<String, Object> message = messageReceivedRequest.getMessage();
        return (message.containsKey(ISkillMessage.TYPE) &&
                message.get(ISkillMessage.TYPE).equals(NotificationMessage.class.getSimpleName()));
    }

    @Override
    public Optional<Response> handle(HandlerInput input, MessageReceivedRequest messageReceivedRequest) {
        String userId = input
                .getRequestEnvelope()
                .getContext()
                .getSystem()
                .getUser()
                .getUserId();

        log.info("Handling notifications for user {}", userId);

        return input.getResponseBuilder().build();
    }
}
