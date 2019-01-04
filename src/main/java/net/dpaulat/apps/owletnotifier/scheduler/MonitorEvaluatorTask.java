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

    @Override
    public long rate(boolean baseStationOn) {
        long rate;
        if (baseStationOn) {
            rate = SchedulerTypes.Frames.TEN_SECONDS;
        } else {
            rate = SchedulerTypes.Frames.FIVE_MINUTES;
        }
        return rate;
    }

    @Override
    public long phase(boolean baseStationOn) {
        long phase;
        if (baseStationOn) {
            phase = 0;
        } else {
            // Phase should be one frame later than setting the app active, as it can take up to ten seconds for
            // data to refresh.
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
