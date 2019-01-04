package net.dpaulat.apps.owletnotifier.scheduler;

import net.dpaulat.apps.ayla.json.AylaAuthorizationByEmail;
import net.dpaulat.apps.owlet.OwletApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
public class RefreshTokenTask implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(RefreshTokenTask.class);

    private final @NotNull TaskScheduler taskScheduler;
    private final @NotNull OwletApi owletApi;

    public RefreshTokenTask(@NotNull TaskScheduler taskScheduler, @NotNull OwletApi owletApi) {
        this.taskScheduler = taskScheduler;
        this.owletApi = owletApi;
    }

    @Override
    public void run() {
        AylaAuthorizationByEmail auth = owletApi.refreshToken();
        scheduleTokenRefresh(auth);
    }

    public void scheduleTokenRefresh(AylaAuthorizationByEmail auth) {
        Long expiresIn = auth.getExpiresIn();
        long offset = expiresIn / 2;
        Date date = Date.from(LocalDateTime.now().plusSeconds(offset).atZone(ZoneId.systemDefault()).toInstant());

        taskScheduler.schedule(this, date);

        log.info("Token refresh scheduled for {}", date);
    }
}
