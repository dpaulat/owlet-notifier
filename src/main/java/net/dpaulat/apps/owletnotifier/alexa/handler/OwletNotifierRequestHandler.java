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

import com.amazon.ask.model.services.reminderManagement.*;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

abstract class OwletNotifierRequestHandler {

    ReminderRequest createReminderRequest(String message, boolean remindNow, boolean pushNotificationEnabled) {
        SpokenText spokenText = SpokenText.builder()
                .withText(message)
                .build();

        AlertInfoSpokenInfo alertInfoSpokenInfo = AlertInfoSpokenInfo.builder()
                .addContentItem(spokenText)
                .build();

        AlertInfo alertInfo = AlertInfo.builder()
                .withSpokenInfo(alertInfoSpokenInfo)
                .build();

        Trigger trigger;
        if (remindNow) {
            trigger = Trigger.builder()
                    .withType(TriggerType.SCHEDULED_RELATIVE)
                    .withOffsetInSeconds(3)
                    .build();
        } else {
            // Place the reminder absurdly in the future
            LocalDateTime triggerTime = LocalDateTime.of(2199, 1, 1, 0, 0, 0);

            trigger = Trigger.builder()
                    .withType(TriggerType.SCHEDULED_ABSOLUTE)
                    .withScheduledTime(triggerTime)
                    .build();
        }

        PushNotification pushNotification = PushNotification.builder()
                .withStatus(pushNotificationEnabled ? PushNotificationStatus.ENABLED : PushNotificationStatus.DISABLED)
                .build();

        return ReminderRequest.builder()
                .withAlertInfo(alertInfo)
                .withRequestTime(OffsetDateTime.now())
                .withTrigger(trigger)
                .withPushNotification(pushNotification)
                .build();
    }
}
