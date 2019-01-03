package net.dpaulat.apps.owletnotifier.monitor;

public interface ICondition {

    public boolean isConditionActive(Monitor monitor, Integer value);
}
