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
import com.amazon.ask.dispatcher.request.handler.impl.MessageReceivedRequestHandler;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.interfaces.messaging.MessageReceivedRequest;
import com.amazon.ask.model.services.reminderManagement.ReminderRequest;
import net.dpaulat.apps.alexa.api.ISkillMessage;
import net.dpaulat.apps.owletnotifier.alexa.data.ReminderEntity;
import net.dpaulat.apps.owletnotifier.alexa.data.ReminderRepository;
import net.dpaulat.apps.owletnotifier.alexa.message.NotificationMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Map;
import java.util.Optional;

@Service
public class NotificationMessageHandler extends OwletNotifierRequestHandler implements MessageReceivedRequestHandler {

    private static final Logger log = LoggerFactory.getLogger(NotificationMessageHandler.class);

    private final ReminderRepository reminderRepository;

    public NotificationMessageHandler(@NotNull ReminderRepository reminderRepository) {
        this.reminderRepository = reminderRepository;
    }

    @Override
    public boolean canHandle(HandlerInput input, MessageReceivedRequest messageReceivedRequest) {
        Map<String, Object> message = messageReceivedRequest.getMessage();
        return (message.containsKey(ISkillMessage.TYPE) &&
                message.get(ISkillMessage.TYPE).equals(NotificationMessage.class.getSimpleName()) &&
                message.containsKey(NotificationMessage.MESSAGE));
    }

    @Override
    public Optional<Response> handle(HandlerInput input, MessageReceivedRequest messageReceivedRequest) {
        boolean pushNotificationEnabled = true;
        String userId = input
                .getRequestEnvelope()
                .getContext()
                .getSystem()
                .getUser()
                .getUserId();

        log.info("Handling notifications for user {}", userId);

        for (ReminderEntity reminder : reminderRepository.findByUserId(userId)) {
            log.info("Sending notification to device {}", reminder.getDeviceId());
            sendNotification(
                    input,
                    reminder,
                    messageReceivedRequest.getMessage().get(NotificationMessage.MESSAGE).toString(),
                    pushNotificationEnabled);
            pushNotificationEnabled = false;
        }

        return input.getResponseBuilder().build();
    }

    private void sendNotification(HandlerInput input, ReminderEntity reminder, String message, boolean pushNotificationEnabled) {
        ReminderRequest notificationRequest = createReminderRequest(
                message,
                true,
                pushNotificationEnabled);

        // Send message
        input.getServiceClientFactory()
                .getReminderManagementService()
                .updateReminder(reminder.getAlertToken(), notificationRequest);
    }
}
