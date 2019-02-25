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

package net.dpaulat.apps.owletnotifier.alexa;

import net.dpaulat.apps.alexa.api.AlexaApi;
import net.dpaulat.apps.alexa.json.AccessTokenResponse;
import net.dpaulat.apps.owletnotifier.alexa.data.ReminderEntity;
import net.dpaulat.apps.owletnotifier.alexa.data.ReminderRepository;
import net.dpaulat.apps.owletnotifier.alexa.message.NotificationMessage;
import net.dpaulat.apps.owletnotifier.alexa.message.SynchronizeReminders;
import net.dpaulat.apps.owletnotifier.events.OwletEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

@Component
public class AlexaScheduler {

    private static final Logger log = LoggerFactory.getLogger(AlexaScheduler.class);

    private final @NotNull AlexaApi alexaApi;
    private final @NotNull AccessTokenTask accessTokenTask;
    private final @NotNull ReminderRepository reminderRepository;
    private boolean initialized;

    public AlexaScheduler(@NotNull AlexaApi alexaApi,
                          @NotNull AccessTokenTask accessTokenTask,
                          @NotNull ReminderRepository reminderRepository) {
        this.alexaApi = alexaApi;
        this.accessTokenTask = accessTokenTask;
        this.reminderRepository = reminderRepository;
        this.initialized = false;
    }

    @Scheduled(fixedRate = Seconds.ONE_HOUR)
    public void process() {
        if (!initialized) {
            initialize();
        } else {
            runOnce();
        }
    }

    private void initialize() {
        log.info("Initializing Alexa Scheduler");

        AccessTokenResponse accessTokenResponse = alexaApi.requestAccessToken();
        if (!alexaApi.isAuthenticated()) {
            log.error("Could not retrieve Alexa skill messaging access token");
        } else {
            accessTokenTask.scheduleTokenRefresh(accessTokenResponse.getExpiresIn() / 2);
        }

        for (ReminderEntity reminder : reminderRepository.findAll()) {
            log.info(reminder.toString());
        }

        // Synchronize reminders for each distinct user
        for (String userId : reminderRepository.findDistinctUserId()) {
            alexaApi.sendSkillMessage(userId, new SynchronizeReminders());
        }

        initialized = true;
    }

    private void runOnce() {
        // Synchronize reminders for each distinct user
        for (String userId : reminderRepository.findDistinctUserId()) {
            alexaApi.sendSkillMessage(userId, new SynchronizeReminders());
        }
    }

    @Async
    @EventListener
    public void handleOwletEvent(OwletEvent oe) {
        log.info("Received Owlet Event: {}", oe.getMessage());

        NotificationMessage message = new NotificationMessage(oe.getMessage());

        for (String userId : reminderRepository.findDistinctUserId()) {
            alexaApi.sendSkillMessage(userId, message);
        }
    }

    private static class Seconds {
        static final long ONE_SECOND = 1000;
        static final long ONE_MINUTE = 60 * ONE_SECOND;
        static final long ONE_HOUR = 60 * ONE_MINUTE;
    }
}
