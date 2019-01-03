package net.dpaulat.apps.owletnotifier.monitor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class ConditionStatus {

    private static final Logger log = LoggerFactory.getLogger(ConditionStatus.class);

    private boolean active;
    private long activeTime;

    public ConditionStatus() {
        active = false;
        activeTime = 0;
    }

    public void activate() {
        this.active = true;
        this.activeTime = System.currentTimeMillis();
        log.info("Activating condition");
    }

    public void deactivate() {
        this.active = false;
        log.info("Deactivating condition");
    }

    public boolean isActive() {
        return active;
    }

    public boolean readyToRepeat(Long repeatTime) {
        return (repeatTime != null) && ((Math.abs(System.currentTimeMillis() - activeTime) / 1000) > repeatTime);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ConditionStatus)) return false;
        ConditionStatus that = (ConditionStatus) o;
        return active == that.active &&
                activeTime == that.activeTime;
    }

    @Override
    public int hashCode() {
        return Objects.hash(active, activeTime);
    }

    @Override
    public String toString() {
        return "ConditionStatus{" +
                "active=" + active +
                ", activeTime=" + activeTime +
                '}';
    }
}
