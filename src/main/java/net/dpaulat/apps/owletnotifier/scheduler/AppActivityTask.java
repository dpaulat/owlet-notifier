package net.dpaulat.apps.owletnotifier.scheduler;

import net.dpaulat.apps.ayla.json.AylaDevice;
import net.dpaulat.apps.owlet.OwletApi;


public class AppActivityTask implements IOwletTask {

    private final OwletApi owletApi;

    public AppActivityTask(OwletApi owletApi) {
        this.owletApi = owletApi;
    }

    @Override
    public long period() {
        return SchedulerTypes.Seconds.THIRTY_SECONDS;
    }

    @Override
    public void run(AylaDevice device) {
        if (owletApi.isMonitoringEnabled(device)) {
            owletApi.setAppActive(device);
        }
    }
}
