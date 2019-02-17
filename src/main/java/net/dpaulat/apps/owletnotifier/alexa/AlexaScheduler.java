package net.dpaulat.apps.owletnotifier.alexa;

import net.dpaulat.apps.alexa.api.AlexaApi;
import net.dpaulat.apps.alexa.json.AccessTokenResponse;
import net.dpaulat.apps.owletnotifier.ConfigProperties;
import net.dpaulat.apps.owletnotifier.alexa.data.ReminderEntity;
import net.dpaulat.apps.owletnotifier.alexa.data.ReminderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

@Component
public class AlexaScheduler {

    private static final Logger log = LoggerFactory.getLogger(AlexaScheduler.class);

    private final @NotNull ApplicationContext context;
    private final @NotNull ConfigProperties config;
    private final @NotNull AlexaApi alexaApi;
    private final @NotNull AccessTokenTask accessTokenTask;
    private final @NotNull ReminderRepository reminderRepository;
    private boolean initialized;

    public AlexaScheduler(@NotNull ApplicationContext context, @NotNull ConfigProperties config, @NotNull AlexaApi alexaApi,
                          @NotNull AccessTokenTask accessTokenTask, @NotNull ReminderRepository reminderRepository) {
        this.context = context;
        this.config = config;
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

        // TODO: Synchronize reminders
        for (ReminderEntity reminder : reminderRepository.findAll()) {
            log.info(reminder.toString());
        }

        initialized = true;
    }

    private void runOnce() {
        // TODO
    }

    private static class Seconds {
        static final long ONE_SECOND = 1000;
        static final long ONE_MINUTE = 60 * ONE_SECOND;
        static final long ONE_HOUR = 60 * ONE_MINUTE;
    }
}
