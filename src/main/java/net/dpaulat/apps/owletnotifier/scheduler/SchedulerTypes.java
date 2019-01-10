package net.dpaulat.apps.owletnotifier.scheduler;

public abstract class SchedulerTypes {

    public static final long FRAME_TIME = Seconds.TEN_SECONDS;

    public static class Seconds {
        public static final long ONE_SECOND = 1000;
        public static final long TEN_SECONDS = 10 * ONE_SECOND;
        public static final long THIRTY_SECONDS = 30 * ONE_SECOND;
    }
}
