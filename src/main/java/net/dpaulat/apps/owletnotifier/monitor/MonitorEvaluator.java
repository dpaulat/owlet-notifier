package net.dpaulat.apps.owletnotifier.monitor;

import net.dpaulat.apps.ayla.json.AylaDevice;
import net.dpaulat.apps.owlet.OwletApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class MonitorEvaluator {

    private Logger log = LoggerFactory.getLogger(MonitorEvaluator.class);

    @Autowired
    private OwletApi owletApi;

    private Map<Monitor, ConditionStatus> conditionStatusMap;

    public MonitorEvaluator() {
        this.conditionStatusMap = new HashMap<>();
    }

    public void evaluate(AylaDevice device, Monitor monitor) {
        ConditionStatus status;
        if (conditionStatusMap.containsKey(monitor)) {
            status = conditionStatusMap.get(monitor);
        } else {
            status = new ConditionStatus();
            conditionStatusMap.put(monitor, status);
        }

        String name = monitor.getName();
        Integer value = owletApi.getPropertyIntValue(device, OwletApi.Properties.toEnum(name));

        log.debug("Evaluating {} [{}]: {}", name, device.getDsn(), value);

        if (value != null && !monitor.getSockReady() || owletApi.isSockReady(device)) {
            if (monitor.getType().getCondition().isConditionActive(monitor, value)) {
                String activeMessage = String.format(monitor.getActiveMessage(),
                        owletApi.getPropertyValue(device, OwletApi.Properties.BABY_NAME),
                        OwletApi.Properties.toEnum(name).getDisplayName().toLowerCase(), value);

                log.warn(activeMessage);

                if (!status.isActive() || status.readyToRepeat(monitor.getRepeatTime())) {
                    status.activate();
                }
            } else if (status.isActive()) {
                String deactivateMessage = String.format(monitor.getDeactivateMessage(),
                        owletApi.getPropertyValue(device, OwletApi.Properties.BABY_NAME),
                        OwletApi.Properties.toEnum(name).getDisplayName().toLowerCase(), value);

                log.info(deactivateMessage);
                status.deactivate();
            }
        }
    }

    private class ConditionStatus {
        boolean active;
        long activeTime;

        ConditionStatus() {
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

        boolean readyToRepeat(Long repeatTime) {
            return (repeatTime != null) && ((Math.abs(System.currentTimeMillis() - activeTime) / 1000) > repeatTime);
        }
    }
}
