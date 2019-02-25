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
import net.dpaulat.apps.owlet.OwletApi;
import net.dpaulat.apps.owletnotifier.ConfigProperties;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Optional;

@Service
public class StartMonitoringIntentHandler implements RequestHandler {

    private final @NotNull ConfigProperties config;
    private final @NotNull OwletApi owletApi;

    public StartMonitoringIntentHandler(@NotNull ConfigProperties config, @NotNull OwletApi owletApi) {
        this.config = config;
        this.owletApi = owletApi;
    }

    @Override
    public boolean canHandle(HandlerInput handlerInput) {
        return handlerInput.matches(Predicates.intentName("StartMonitoring"));
    }

    @Override
    public Optional<Response> handle(HandlerInput handlerInput) {
        owletApi.setAllMonitoringEnabled(true);
        String speechText = "OK, monitoring is enabled.";

        return handlerInput.getResponseBuilder()
                .withSpeech(speechText)
                .withSimpleCard(config.getAlexa().getCardTitle(), speechText)
                .withReprompt(speechText)
                .build();
    }
}
