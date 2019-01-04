package net.dpaulat.apps.owletnotifier;

import net.dpaulat.apps.ayla.json.AylaDevice;
import net.dpaulat.apps.owlet.OwletApi;
import net.dpaulat.apps.owletnotifier.monitor.Monitor;
import net.dpaulat.apps.owletnotifier.monitor.MonitorEvaluator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ScheduledTasks {

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    @Autowired
    private ApplicationContext context;
    @Autowired
    private ConfigProperties config;
    @Autowired
    private MonitorEvaluator monitorEvaluator;
    @Autowired
    private OwletApi owletApi;
    private boolean initialized;
    private List<AylaDevice> deviceList;

    public ScheduledTasks() {
        this.initialized = false;
        this.deviceList = null;
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

            for (AylaDevice device : deviceList) {
                owletApi.updateProperties(device);
                owletApi.setAppActive(device);
            }
        }

        // TODO: Determine when to refresh access token

        for (AylaDevice device : deviceList) {
            // TODO: Update APP_ACTIVE to 1
            // TODO: Change rate if base station is off or the sock is charging (5 minutes vs. 10 seconds)
            owletApi.updateProperties(device);
            for (Monitor monitor : config.getOwlet().getMonitors()) {
                monitorEvaluator.evaluate(device, monitor);
            }
        }
    }
}
