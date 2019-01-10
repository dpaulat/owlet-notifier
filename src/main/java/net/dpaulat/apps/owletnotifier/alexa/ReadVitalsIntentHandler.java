package net.dpaulat.apps.owletnotifier.alexa;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import com.amazon.ask.request.Predicates;
import net.dpaulat.apps.ayla.json.AylaDevice;
import net.dpaulat.apps.owlet.OwletApi;
import net.dpaulat.apps.owlet.OwletProperties;
import net.dpaulat.apps.owletnotifier.ConfigProperties;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Optional;

@Service
public class ReadVitalsIntentHandler implements RequestHandler {

    private final @NotNull ConfigProperties config;
    private final @NotNull OwletApi owletApi;

    public ReadVitalsIntentHandler(@NotNull ConfigProperties config, @NotNull OwletApi owletApi) {
        this.config = config;
        this.owletApi = owletApi;
    }

    @Override
    public boolean canHandle(HandlerInput handlerInput) {
        return handlerInput.matches(Predicates.intentName("ReadVitals"));
    }

    @Override
    public Optional<Response> handle(HandlerInput handlerInput) {
        String speechText;
        AylaDevice device = owletApi.getDefaultDevice();

        if (device == null) {
            speechText = "Cannot find device to read vitals from.";
        } else {
            String babyName = owletApi.getPropertyValue(device, OwletProperties.BABY_NAME);
            String heartRate = owletApi.getPropertyValue(device, OwletProperties.HEART_RATE);
            String oxygenLevel = owletApi.getPropertyValue(device, OwletProperties.OXYGEN_LEVEL);

            speechText = String.format("%s's heart rate is %s, and oxygen level is %s.", babyName, heartRate,
                    oxygenLevel);
        }

        return handlerInput.getResponseBuilder()
                .withSpeech(speechText)
                .withSimpleCard(config.getAlexa().getCardTitle(), speechText)
                .withReprompt(speechText)
                .build();
    }
}