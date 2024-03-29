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

package net.dpaulat.apps.owletnotifier.alexa.handler;

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
import java.util.List;
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
        List<AylaDevice> devices = owletApi.getDevices();


        if (devices == null || devices.size() == 0) {
            speechText = "Cannot find device to read vitals from.";
        } else {
            StringBuilder speechTextBuilder = new StringBuilder();
            for (AylaDevice device : devices) {
                if (owletApi.isMonitoringEnabled(device)) {
                    final String babyName = owletApi.getPropertyValue(device, OwletProperties.BABY_NAME, String.class);
                    final Boolean baseStationOn = owletApi.getPropertyValue(device, OwletProperties.BASE_STATION_ON, Boolean.class);
                    final Integer chargeStatus = owletApi.getPropertyValue(device, OwletProperties.CHARGE_STATUS, Integer.class);
                    final Boolean movement = owletApi.getPropertyValue(device, OwletProperties.MOVEMENT, Boolean.class);
                    final Boolean sockRecentlyPlaced = owletApi.getPropertyValue(device, OwletProperties.SOCK_REC_PLACED, Boolean.class);
                    final Integer heartRate = owletApi.getPropertyValue(device, OwletProperties.HEART_RATE, Integer.class);
                    final Integer oxygenLevel = owletApi.getPropertyValue(device, OwletProperties.OXYGEN_LEVEL, Integer.class);

                    if (chargeStatus != null && chargeStatus > 0) {
                        speechTextBuilder.append(String.format("%s's sock is charging. ", babyName));
                    } else if (baseStationOn != null && !baseStationOn) {
                        speechTextBuilder.append(String.format("%s's base station is off. ", babyName));
                    } else if (sockRecentlyPlaced != null && sockRecentlyPlaced) {
                        speechTextBuilder.append(String.format("%s's vitals are pending. ", babyName));
                    } else if (movement != null && movement) {
                        speechTextBuilder.append(String.format("%s is moving. ", babyName));
                    } else if (heartRate != null && oxygenLevel != null) {
                        speechTextBuilder.append(String.format("%s's heart rate is %d, and oxygen level is %d. ", babyName,
                                heartRate, oxygenLevel));
                    } else {
                        speechTextBuilder.append(String.format("%s's vitals are pending. ", babyName));
                    }
                }
            }

            if (speechTextBuilder.length() == 0) {
                speechTextBuilder.append("Monitoring is currently disabled.");
            }

            speechText = speechTextBuilder.toString();
        }

        return handlerInput.getResponseBuilder()
                .withSpeech(speechText)
                .withSimpleCard(config.getAlexa().getCardTitle(), speechText)
                .withReprompt(speechText)
                .build();
    }
}
