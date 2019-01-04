package net.dpaulat.apps.owletnotifier.scheduler;

import net.dpaulat.apps.ayla.json.AylaDevice;
import net.dpaulat.apps.owlet.OwletApi;


public class AppActivityTask implements IOwletTask {

    private final OwletApi owletApi;

    public AppActivityTask(OwletApi owletApi) {
        this.owletApi = owletApi;
    }

    @Override
    public long rate(boolean baseStationOn) {
        long rate;
        if (baseStationOn) {
            rate = SchedulerTypes.Frames.THIRTY_SECONDS;
        } else {
            rate = SchedulerTypes.Frames.FIVE_MINUTES;
        }
        return rate;
    }

    @Override
    public long phase(boolean baseStationOn) {
        return 0;
    }

    @Override
    public void run(AylaDevice device) {
        owletApi.setAppActive(device);
    }
}
