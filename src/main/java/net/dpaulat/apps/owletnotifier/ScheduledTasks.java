package net.dpaulat.apps.owletnotifier;

import net.dpaulat.apps.ayla.json.AylaDevice;
import net.dpaulat.apps.owlet.OwletApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ScheduledTasks {

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    @Autowired
    private ApplicationContext context;
    @Autowired
    private ConfigProperties config;
    private OwletApi owletApi;
    private boolean initialized;
    private List<AylaDevice> deviceList;
    private Map<ConfigProperties.Owlet.Monitor, MonitorStatus> monitorStatusMap;

    public ScheduledTasks() {
        this.owletApi = new OwletApi();
        this.initialized = false;
        this.deviceList = null;
        this.monitorStatusMap = new HashMap<>();
    }

    private static Integer tryParseInt(String value) {
        Integer i = null;

        try {
            i = Integer.valueOf(value);
        } catch (NumberFormatException ex) {
        }

        return i;
    }

    @Scheduled(fixedRate = 10000)
    public void process() {
        if (!initialized) {
            initialized = true;
            log.info("Initializing Owlet Monitor");
            log.debug(config.toString());

            owletApi.signIn(config.getOwlet().getEmail(), config.getOwlet().getPassword());
            if (!owletApi.isSignedIn()) {
                log.error("Could not sign in, exiting");
                SpringApplication.exit(context, () -> -1);
            }

            deviceList = owletApi.retrieveDevices();
        }

        // TODO: Determine when to refresh access token

        for (AylaDevice device : deviceList) {
            // TODO: Update APP_ACTIVE to 1
            owletApi.updateProperties(device);
            for (ConfigProperties.Owlet.Monitor monitor : config.getOwlet().getMonitors()) {
                evaluateMonitor(monitor, device);
            }
        }
    }

    private void evaluateMonitor(ConfigProperties.Owlet.Monitor monitor, AylaDevice device) {
        MonitorStatus status;
        if (monitorStatusMap.containsKey(monitor)) {
            status = monitorStatusMap.get(monitor);
        } else {
            status = new MonitorStatus();
            monitorStatusMap.put(monitor, status);
        }

        String name = monitor.getName();
        String value = owletApi.getPropertyValue(device, OwletApi.Properties.toEnum(name));

        String message = String.format("%s's %s is %s",
                owletApi.getPropertyValue(device, OwletApi.Properties.BABY_NAME),
                OwletApi.Properties.toEnum(name).getDisplayName().toLowerCase(), value);
        log.debug("Evaluating {} [{}]: {}", name, device.getDsn(), value);

        Integer intValue = tryParseInt(value);

        if (intValue != null) {
            if (monitor.getMinimumValue() != null) {
                if (intValue < monitor.getMinimumValue()) {
                    log.warn(message);

                    if (status.getMinimumCondition().hasTimeElapsed(monitor.getRepeatTime())) {
                        status.getMinimumCondition().activate();
                    }
                } else if (status.getMinimumCondition().isActive()) {
                    log.info(message);
                    status.getMinimumCondition().deactivate();
                }
            }
            if (monitor.getMaximumValue() != null) {
                if (intValue > monitor.getMaximumValue()) {
                    log.warn(message);

                    if (status.getMaximumCondition().hasTimeElapsed(monitor.getRepeatTime())) {
                        status.getMaximumCondition().activate();
                    }
                } else if (status.getMaximumCondition().isActive()) {
                    log.info(message);
                    status.getMaximumCondition().deactivate();
                }
            }
        }
    }

    private static class MonitorStatus {
        final Condition minimumCondition;
        final Condition maximumCondition;

        MonitorStatus() {
            minimumCondition = new Condition();
            maximumCondition = new Condition();
        }

        public Condition getMinimumCondition() {
            return minimumCondition;
        }

        public Condition getMaximumCondition() {
            return maximumCondition;
        }

        static class Condition {
            boolean active;
            long activeTime;

            Condition() {
                active = false;
                activeTime = 0;
            }

            void activate() {
                this.active = true;
                this.activeTime = System.currentTimeMillis();
                log.info("Activating condition");
            }

            void deactivate() {
                this.active = false;
                log.info("Deactivating condition");
            }

            boolean isActive() {
                return active;
            }

            private boolean hasTimeElapsed(Long seconds) {
                return !active ||
                        (seconds != null && (Math.abs(System.currentTimeMillis() - activeTime) / 1000 > seconds));
            }
        }
    }
}
