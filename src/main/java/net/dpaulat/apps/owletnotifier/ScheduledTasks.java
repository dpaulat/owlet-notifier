package net.dpaulat.apps.owletnotifier;

import net.dpaulat.apps.ayla.json.AylaDevice;
import net.dpaulat.apps.owlet.OwletApi;
import net.dpaulat.apps.owlet.OwletProperties;
import net.dpaulat.apps.owletnotifier.monitor.Monitor;
import net.dpaulat.apps.owletnotifier.monitor.MonitorEvaluator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Component
public class ScheduledTasks {

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);
    private static final long frameTime = Seconds.TEN_SECONDS;

    private final @NotNull ApplicationContext context;
    private final @NotNull ConfigProperties config;
    private final @NotNull MonitorEvaluator monitorEvaluator;
    private final @NotNull OwletApi owletApi;
    private final @NotNull TaskScheduler executor;
    private final List<IOwletTask> taskList;
    private boolean initialized;
    private long frameCounter;
    private List<AylaDevice> deviceList;

    public ScheduledTasks(@NotNull ApplicationContext context, @NotNull ConfigProperties config,
                          @NotNull MonitorEvaluator monitorEvaluator, @NotNull OwletApi owletApi,
                          @NotNull TaskScheduler executor) {
        this.context = context;
        this.config = config;
        this.monitorEvaluator = monitorEvaluator;
        this.owletApi = owletApi;
        this.executor = executor;
        this.initialized = false;
        this.frameCounter = 0;
        this.deviceList = null;

        this.taskList = new ArrayList<>();
        taskList.add(new SetAppActiveTask());
        taskList.add(new EvaluatorTask());
    }

    private static long periodToFrames(long period) {
        return period / frameTime;
    }

    @Scheduled(fixedRate = frameTime)
    public void process() {
        if (!initialized) {
            initialize();
        } else {
            runOnce();
        }
    }

    private void initialize() {
        log.info("Initializing Owlet Monitor");
        log.debug(config.toString());

        owletApi.signIn(config.getOwlet().getEmail(), config.getOwlet().getPassword());
        if (!owletApi.isSignedIn()) {
            log.error("Could not sign in, exiting");
            SpringApplication.exit(context, () -> -1);
        }

        deviceList = owletApi.retrieveDevices();

        for (AylaDevice device : deviceList) {
            owletApi.setAppActive(device);
            owletApi.updateProperties(device);
        }

        initialized = true;
    }

    private void runOnce() {
        // TODO: Determine when to refresh access token
        for (AylaDevice device : deviceList) {
            Integer baseStationOnInt = owletApi.getPropertyIntValue(device, OwletProperties.BASE_STATION_ON);
            boolean baseStationOn = (baseStationOnInt != 0);

            for (IOwletTask task : taskList) {
                if (frameCounter % task.rate(baseStationOn) == task.phase(baseStationOn)) {
                    task.run(device);
                }
            }
        }

        frameCounter++;
    }

    private interface IOwletTask {
        long rate(boolean baseStationOn);

        long phase(boolean baseStationOn);

        void run(AylaDevice device);
    }

    private static class Seconds {
        private static final long ONE_SECOND = 1000;
        private static final long TEN_SECONDS = 10 * ONE_SECOND;
        private static final long THIRTY_SECONDS = 30 * ONE_SECOND;
        private static final long ONE_MINUTE = 60 * ONE_SECOND;
        private static final long FIVE_MINUTES = 5 * ONE_MINUTE;
    }

    private static class Frames {
        private static final long TEN_SECONDS = periodToFrames(Seconds.TEN_SECONDS);
        private static final long THIRTY_SECONDS = periodToFrames(Seconds.THIRTY_SECONDS);
        private static final long FIVE_MINUTES = periodToFrames(Seconds.FIVE_MINUTES);
    }

    private class SetAppActiveTask implements IOwletTask {

        @Override
        public long rate(boolean baseStationOn) {
            long rate;
            if (baseStationOn) {
                rate = Frames.THIRTY_SECONDS;
            } else {
                rate = Frames.FIVE_MINUTES;
            }
            return rate;
        }

        @Override
        public long phase(boolean baseStationOn) {
            return 0;
        }

        @Override
        public void run(AylaDevice device) {
            owletApi.setAppActive(device);
        }
    }

    private class EvaluatorTask implements IOwletTask {

        @Override
        public long rate(boolean baseStationOn) {
            long rate;
            if (baseStationOn) {
                rate = Frames.TEN_SECONDS;
            } else {
                rate = Frames.FIVE_MINUTES;
            }
            return rate;
        }

        @Override
        public long phase(boolean baseStationOn) {
            long phase;
            if (baseStationOn) {
                phase = 0;
            } else {
                /* Phase should be one frame later than setting the app active, as it can take up to ten seconds for
                 * data to refresh.
                 */
                phase = 1;
            }
            return phase;
        }

        @Override
        public void run(AylaDevice device) {
            owletApi.updateProperties(device);
            for (Monitor monitor : config.getOwlet().getMonitors()) {
                monitorEvaluator.evaluate(device, monitor);
            }
        }
    }
}
