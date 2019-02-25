package net.dpaulat.apps.owletnotifier.scheduler;

import net.dpaulat.apps.ayla.json.AylaAuthorizationByEmail;
import net.dpaulat.apps.owlet.OwletApi;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

@Component
public class RefreshTokenTask extends RefreshTask {

    private final @NotNull OwletApi owletApi;

    public RefreshTokenTask(@NotNull TaskScheduler taskScheduler, @NotNull OwletApi owletApi) {
        super(taskScheduler);

        this.owletApi = owletApi;
    }

    @Override
    public void run() {
        AylaAuthorizationByEmail auth = owletApi.refreshToken();
        scheduleTokenRefresh(auth.getExpiresIn() / 2);
    }
}
