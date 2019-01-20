package net.dpaulat.apps.owletnotifier.monitor;

public interface ICondition {

    boolean isConditionActive(Monitor monitor, Integer value);
}
