package net.dpaulat.apps.owletnotifier.scheduler;

import net.dpaulat.apps.ayla.json.AylaAuthorizationByEmail;
import net.dpaulat.apps.ayla.json.AylaDevice;
import net.dpaulat.apps.owlet.OwletApi;
import net.dpaulat.apps.owletnotifier.ConfigProperties;
import net.dpaulat.apps.owletnotifier.monitor.MonitorEvaluator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class OwletScheduler {

    private static final Logger log = LoggerFactory.getLogger(OwletScheduler.class);

    private final @NotNull ApplicationContext context;
    private final @NotNull ConfigProperties config;
    private final @NotNull OwletApi owletApi;
    private final @NotNull RefreshTokenTask refreshTokenTask;
    private final List<IOwletTask> periodicTaskList;
    private final Map<IOwletTask, Long> taskLastRunMap;
    private boolean initialized;
    private List<AylaDevice> deviceList;

    public OwletScheduler(@NotNull ApplicationContext context, @NotNull ConfigProperties config,
                          @NotNull MonitorEvaluator monitorEvaluator, @NotNull OwletApi owletApi,
                          @NotNull RefreshTokenTask refreshTokenTask) {
        this.context = context;
        this.config = config;
        this.owletApi = owletApi;
        this.refreshTokenTask = refreshTokenTask;
        this.initialized = false;
        this.deviceList = null;

        this.periodicTaskList = new ArrayList<>();
        periodicTaskList.add(new AppActivityTask(owletApi));
        periodicTaskList.add(new MonitorEvaluatorTask(config, owletApi, monitorEvaluator));

        this.taskLastRunMap = new HashMap<>();
    }

    @Scheduled(fixedRate = SchedulerTypes.FRAME_TIME)
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

        AylaAuthorizationByEmail auth = owletApi.signIn(config.getOwlet().getEmail(), config.getOwlet().getPassword());
        if (!owletApi.isSignedIn()) {
            log.error("Could not sign in, exiting");
            SpringApplication.exit(context, () -> -1);
        }

        refreshTokenTask.scheduleTokenRefresh(auth);

        deviceList = owletApi.retrieveDevices();

        for (AylaDevice device : deviceList) {
            owletApi.setAppActive(device);
            owletApi.updateProperties(device);
        }

        initialized = true;
    }

    private boolean periodHasElapsed(IOwletTask task, long taskLastRun) {
        final long epsilon = SchedulerTypes.FRAME_TIME / 2;
        return (taskLastRun + task.period() - epsilon <= System.currentTimeMillis());
    }

    private void runOnce() {
        for (AylaDevice device : deviceList) {
            for (IOwletTask task : periodicTaskList) {
                Long taskLastRun = taskLastRunMap.get(task);
                if (taskLastRun == null || periodHasElapsed(task, taskLastRun)) {
                    // Log time task was started
                    taskLastRun = System.currentTimeMillis();
                    taskLastRunMap.put(task, taskLastRun);

                    // Run task
                    task.run(device);
                }
            }
        }
    }

}
