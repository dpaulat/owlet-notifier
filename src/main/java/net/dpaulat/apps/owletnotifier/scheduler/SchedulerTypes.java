package net.dpaulat.apps.owletnotifier.scheduler;

public abstract class SchedulerTypes {

    public static final long FRAME_TIME = Seconds.TEN_SECONDS;

    public static long periodToFrames(long period) {
        return period / FRAME_TIME;
    }

    public static class Seconds {
        public static final long ONE_SECOND = 1000;
        public static final long TEN_SECONDS = 10 * ONE_SECOND;
        public static final long THIRTY_SECONDS = 30 * ONE_SECOND;
        public static final long ONE_MINUTE = 60 * ONE_SECOND;
        public static final long FIVE_MINUTES = 5 * ONE_MINUTE;
    }

    public static class Frames {
        public static final long TEN_SECONDS = periodToFrames(Seconds.TEN_SECONDS);
        public static final long THIRTY_SECONDS = periodToFrames(Seconds.THIRTY_SECONDS);
        public static final long FIVE_MINUTES = periodToFrames(Seconds.FIVE_MINUTES);
    }
}
