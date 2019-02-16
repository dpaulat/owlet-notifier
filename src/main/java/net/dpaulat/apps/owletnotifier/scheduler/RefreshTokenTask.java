package net.dpaulat.apps.owletnotifier.scheduler;

import net.dpaulat.apps.ayla.json.AylaAuthorizationByEmail;
import net.dpaulat.apps.owlet.OwletApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

@Component
public class RefreshTokenTask extends RefreshTask {

    private static final Logger log = LoggerFactory.getLogger(RefreshTokenTask.class);

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
