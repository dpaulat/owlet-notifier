package net.dpaulat.apps.owletnotifier.monitor;

import net.dpaulat.apps.ayla.json.AylaDevice;
import net.dpaulat.apps.owlet.OwletApi;
import net.dpaulat.apps.owlet.OwletProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MonitorEvaluator {

    private static final Logger log = LoggerFactory.getLogger(MonitorEvaluator.class);

    @Autowired
    private OwletApi owletApi;

    public MonitorEvaluator() {
    }

    public void evaluate(AylaDevice device, Monitor monitor) {
        OwletProperties property = monitor.getProperty();
        Integer value = owletApi.getPropertyIntValue(device, property);

        log.debug("Evaluating {} [{}]: {}", property.getDisplayName(), device.getDsn(), value);

        if (value != null && !monitor.getSockReady() || owletApi.isSockReady(device)) {
            if (monitor.getType().getCondition().isConditionActive(monitor, value)) {
                String activeMessage = String.format(monitor.getActiveMessage(),
                        owletApi.getPropertyValue(device, OwletProperties.BABY_NAME),
                        property.getDisplayName().toLowerCase(), value);

                log.warn(activeMessage);

                if (!monitor.getStatus().isActive() || monitor.getStatus().readyToRepeat(monitor.getRepeatTime())) {
                    monitor.getStatus().activate();
                }
            } else if (monitor.getStatus().isActive()) {
                String deactivateMessage = String.format(monitor.getDeactivateMessage(),
                        owletApi.getPropertyValue(device, OwletProperties.BABY_NAME),
                        property.getDisplayName().toLowerCase(), value);

                log.info(deactivateMessage);
                monitor.getStatus().deactivate();
            }
        }
    }
}
