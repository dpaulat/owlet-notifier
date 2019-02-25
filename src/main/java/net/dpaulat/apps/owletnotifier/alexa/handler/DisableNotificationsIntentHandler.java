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
import com.amazon.ask.model.Permissions;
import com.amazon.ask.model.Response;
import com.amazon.ask.request.Predicates;
import net.dpaulat.apps.owletnotifier.ConfigProperties;
import net.dpaulat.apps.owletnotifier.alexa.data.ReminderEntity;
import net.dpaulat.apps.owletnotifier.alexa.data.ReminderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Optional;

@Service
public class DisableNotificationsIntentHandler implements RequestHandler {

    private static final Logger log = LoggerFactory.getLogger(DisableNotificationsIntentHandler.class);

    private final @NotNull ConfigProperties config;
    private final @NotNull ReminderRepository reminderRepository;

    public DisableNotificationsIntentHandler(@NotNull ConfigProperties config,
                                             @NotNull ReminderRepository reminderRepository) {
        this.config = config;
        this.reminderRepository = reminderRepository;
    }

    @Override
    public boolean canHandle(HandlerInput handlerInput) {
        return handlerInput.matches(Predicates.intentName("DisableNotifications"));
    }

    @Override
    public Optional<Response> handle(HandlerInput handlerInput) {
        String speechText = "Notifications are already disabled for this device.";

        Permissions permissions = handlerInput
                .getRequestEnvelope()
                .getContext()
                .getSystem()
                .getUser()
                .getPermissions();

        if (permissions == null || permissions.getConsentToken() == null) {
            return handlerInput.getResponseBuilder()
                    .withSpeech(speechText)
                    .withSimpleCard(config.getAlexa().getCardTitle(), speechText)
                    .withReprompt(speechText)
                    .build();
        }

        String deviceId = handlerInput
                .getRequestEnvelope()
                .getContext()
                .getSystem()
                .getDevice()
                .getDeviceId();

        log.info("Disabling notifications for {}", deviceId);

        Optional<ReminderEntity> storedReminder = reminderRepository.findByDeviceId(deviceId);
        if (storedReminder.isPresent()) {
            try {
                // Delete the reminder remotely
                handlerInput.getServiceClientFactory()
                        .getReminderManagementService()
                        .deleteReminder(storedReminder.get().getAlertToken());
            } catch (Exception ex) {
                log.info("Error removing remote reminder (reminder is likely completed), proceeding anyway");
            }

            // Delete the reminder locally
            reminderRepository.delete(storedReminder.get());

            speechText = "OK, notifications have been disabled on this device.";
        }

        return handlerInput.getResponseBuilder()
                .withSpeech(speechText)
                .withSimpleCard(config.getAlexa().getCardTitle(), speechText)
                .withReprompt(speechText)
                .build();
    }
}
