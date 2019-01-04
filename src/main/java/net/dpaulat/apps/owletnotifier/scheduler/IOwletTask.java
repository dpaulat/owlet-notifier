package net.dpaulat.apps.owletnotifier.scheduler;

import net.dpaulat.apps.ayla.json.AylaDevice;

public interface IOwletTask {
    long rate(boolean baseStationOn);

    long phase(boolean baseStationOn);

    void run(AylaDevice device);
}
