package net.dpaulat.apps.owletnotifier.scheduler;

import net.dpaulat.apps.ayla.json.AylaDevice;

public interface IOwletTask {
    long period();

    void run(AylaDevice device);
}
