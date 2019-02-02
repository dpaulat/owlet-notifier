package net.dpaulat.apps.owletnotifier.alexa;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Permissions;
import com.amazon.ask.model.Response;
import com.amazon.ask.request.Predicates;
import net.dpaulat.apps.owlet.OwletApi;
import net.dpaulat.apps.owletnotifier.ConfigProperties;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EnableNotificationsIntentHandler implements RequestHandler {

    private final @NotNull ConfigProperties config;
    private final @NotNull OwletApi owletApi;

    public EnableNotificationsIntentHandler(@NotNull ConfigProperties config, @NotNull OwletApi owletApi) {
        this.config = config;
        this.owletApi = owletApi;
    }

    @Override
    public boolean canHandle(HandlerInput handlerInput) {
        return handlerInput.matches(Predicates.intentName("EnableNotifications"));
    }

    @Override
    public Optional<Response> handle(HandlerInput handlerInput) {

        Permissions permissions = handlerInput
                .getRequestEnvelope()
                .getContext()
                .getSystem()
                .getUser()
                .getPermissions();

        if (permissions == null || permissions.getConsentToken() == null) {
            String speechText = "In order to enable notifications, this skill needs permission to create reminders.  Please grant permission using the card sent to your Alexa app.";
            List<String> list = new ArrayList<>();
            list.add("alexa::alerts:reminders:skill:readwrite");
            return handlerInput.getResponseBuilder()
                    .withSpeech(speechText)
                    .withAskForPermissionsConsentCard(list)
                    .build();
        }

        String speechText = "OK, notifications are enabled on this device using reminders.";

        return handlerInput.getResponseBuilder()
                .withSpeech(speechText)
                .withSimpleCard(config.getAlexa().getCardTitle(), speechText)
                .withReprompt(speechText)
                .build();
    }
}
