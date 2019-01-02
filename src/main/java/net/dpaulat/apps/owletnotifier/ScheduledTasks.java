package net.dpaulat.apps.owletnotifier;

import net.dpaulat.apps.ayla.json.AylaDevice;
import net.dpaulat.apps.owlet.OwletApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ScheduledTasks {

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    @Autowired
    private ConfigProperties config;

    private OwletApi owletApi;
    private boolean initialized;

    public ScheduledTasks() {
        this.owletApi = new OwletApi();
        this.initialized = false;
    }

    @Scheduled(fixedRate = 30000)
    public void process() {
        log.info("Ping");
        if (!initialized) {
            initialized = true;
            log.debug(config.toString());
            log.debug(config.getOwlet().toString());
            for (ConfigProperties.Owlet.Monitor monitor : config.getOwlet().getMonitors()) {
                log.debug(monitor.toString());
            }
            owletApi.signIn(config.getOwlet().getEmail(), config.getOwlet().getPassword());
            owletApi.refreshToken();
            List<AylaDevice> deviceList = owletApi.retrieveDevices();
            for (AylaDevice device : deviceList) {
                owletApi.updateProperties(device, (name, oldValue, newValue) -> {
                    if (name.equals(OwletApi.Properties.OXYGEN_LEVEL.name())) {
                        log.debug("{}'s oxygen level changed from {} to {}",
                                owletApi.getPropertyValue(device, OwletApi.Properties.BABY_NAME), oldValue, newValue);
                    }
                });
            }
        }
    }
}
