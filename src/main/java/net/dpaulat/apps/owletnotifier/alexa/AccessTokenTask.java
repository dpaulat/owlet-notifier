package net.dpaulat.apps.owletnotifier.alexa;

import net.dpaulat.apps.alexa.api.AlexaApi;
import net.dpaulat.apps.alexa.json.AccessTokenResponse;
import net.dpaulat.apps.owletnotifier.scheduler.RefreshTask;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

@Component
public class AccessTokenTask extends RefreshTask {

    private final @NotNull AlexaApi alexaApi;

    public AccessTokenTask(@NotNull TaskScheduler taskScheduler, @NotNull AlexaApi alexaApi) {
        super(taskScheduler);

        this.alexaApi = alexaApi;
    }

    @Override
    public void run() {
        AccessTokenResponse accessToken = alexaApi.requestAccessToken();
        if (accessToken != null) {
            scheduleTokenRefresh(accessToken.getExpiresIn() / 2);
        } else {
            scheduleTokenRefresh(60);
        }
    }
}
