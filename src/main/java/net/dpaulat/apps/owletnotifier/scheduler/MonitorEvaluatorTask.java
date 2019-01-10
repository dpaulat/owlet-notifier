package net.dpaulat.apps.owletnotifier.scheduler;

import net.dpaulat.apps.ayla.json.AylaDevice;
import net.dpaulat.apps.owlet.OwletApi;
import net.dpaulat.apps.owletnotifier.ConfigProperties;
import net.dpaulat.apps.owletnotifier.monitor.Monitor;
import net.dpaulat.apps.owletnotifier.monitor.MonitorEvaluator;

public class MonitorEvaluatorTask implements IOwletTask {

    private final ConfigProperties config;
    private final OwletApi owletApi;
    private final MonitorEvaluator monitorEvaluator;

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
        if (owletApi.isMonitoringEnabled()) {
            owletApi.updateProperties(device);
            for (Monitor monitor : config.getOwlet().getMonitors()) {
                monitorEvaluator.evaluate(device, monitor);
            }
        }
    }
}
