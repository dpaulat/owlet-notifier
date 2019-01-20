package net.dpaulat.apps.owletnotifier.monitor;

public enum ConditionType {
    MinimumValue((monitor, value) -> value < monitor.getValue()),
    MaximumValue((monitor, value) -> value > monitor.getValue()),
    Equals((monitor, value) -> value.equals(monitor.getValue()));

    private final ICondition condition;

    ConditionType(ICondition condition) {
        this.condition = condition;
    }

    public ICondition getCondition() {
        return condition;
    }
}
