package net.dpaulat.apps.owletnotifier.scheduler;

import net.dpaulat.apps.ayla.json.AylaDevice;
import net.dpaulat.apps.owlet.OwletApi;
import net.dpaulat.apps.owlet.OwletProperties;
import net.dpaulat.apps.owletnotifier.ConfigProperties;
import net.dpaulat.apps.owletnotifier.monitor.Monitor;
import net.dpaulat.apps.owletnotifier.monitor.MonitorEvaluator;

public class MonitorEvaluatorTask implements IOwletTask {

    private final ConfigProperties config;
    private final OwletApi owletApi;
    private final MonitorEvaluator monitorEvaluator;

    private Integer disableCounter = 0;

    public MonitorEvaluatorTask(ConfigProperties config, OwletApi owletApi, MonitorEvaluator monitorEvaluator) {

        this.config = config;
        this.owletApi = owletApi;
        this.monitorEvaluator = monitorEvaluator;
    }

    public long period() {
        return SchedulerTypes.Seconds.TEN_SECONDS;
    }

    @Override
    public void run(AylaDevice device) {
        if (owletApi.isMonitoringEnabled(device)) {
            owletApi.updateProperties(device);
            for (Monitor monitor : config.getOwlet().getMonitors()) {
                monitorEvaluator.evaluate(device, monitor);
            }
            evaluateMonitoringConditions(device);
        }
    }

    private void evaluateMonitoringConditions(AylaDevice device) {
        Boolean baseStationOn = owletApi.getPropertyValue(device, OwletProperties.BASE_STATION_ON, Boolean.class);
        Integer chargeStatus = owletApi.getPropertyValue(device, OwletProperties.CHARGE_STATUS, Integer.class);

        // If the base station is turned off, or the sock is plugged in, turn off monitoring
        if ((baseStationOn != null && !baseStationOn) ||
            (chargeStatus != null && chargeStatus > 0)) {
            disableCounter++;
        } else {
            disableCounter = 0;
        }

        if (disableCounter > 5) {
            owletApi.setMonitoringEnabled(device, false);
            disableCounter = 0;
        }
    }
}
