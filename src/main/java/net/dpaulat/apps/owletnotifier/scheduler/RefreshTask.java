package net.dpaulat.apps.owletnotifier.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.TaskScheduler;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public abstract class RefreshTask implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(RefreshTask.class);

    private final @NotNull TaskScheduler taskScheduler;

    public RefreshTask(@NotNull TaskScheduler taskScheduler) {
        this.taskScheduler = taskScheduler;
    }

    public void scheduleTokenRefresh(long secondsFromNow) {
        Date date = Date.from(LocalDateTime.now().plusSeconds(secondsFromNow).atZone(ZoneId.systemDefault()).toInstant());

        taskScheduler.schedule(this, date);

        log.info("{} scheduled for {}", this.getClass().getSimpleName(), date);
    }
}
