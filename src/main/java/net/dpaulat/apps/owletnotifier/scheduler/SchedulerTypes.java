package net.dpaulat.apps.owletnotifier.scheduler;

abstract class SchedulerTypes {

    static final long FRAME_TIME = Seconds.TEN_SECONDS;

    static class Seconds {
        static final long ONE_SECOND = 1000;
        static final long TEN_SECONDS = 10 * ONE_SECOND;
        static final long THIRTY_SECONDS = 30 * ONE_SECOND;
    }
}
