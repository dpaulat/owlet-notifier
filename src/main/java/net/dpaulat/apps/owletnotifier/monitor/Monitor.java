package net.dpaulat.apps.owletnotifier.monitor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

public class Monitor {
    @NotBlank
    private String name;
    @NotNull
    private ConditionType type;
    @NotNull
    private Long value;
    private Long repeatTime;
    @NotNull
    private Boolean sockReady = false;
    @NotBlank
    private String activeMessage = "%1$s's %2$s is %3$s";
    @NotBlank
    private String deactivateMessage = "%1$s's %2$s is %3$s";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ConditionType getType() {
        return type;
    }

    public void setType(ConditionType type) {
        this.type = type;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public Long getRepeatTime() {
        return repeatTime;
    }

    public void setRepeatTime(Long repeatTime) {
        this.repeatTime = repeatTime;
    }

    public Boolean getSockReady() {
        return sockReady;
    }

    public void setSockReady(Boolean sockReady) {
        this.sockReady = sockReady;
    }

    public String getActiveMessage() {
        return activeMessage;
    }

    public void setActiveMessage(String activeMessage) {
        this.activeMessage = activeMessage;
    }

    public String getDeactivateMessage() {
        return deactivateMessage;
    }

    public void setDeactivateMessage(String deactivateMessage) {
        this.deactivateMessage = deactivateMessage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Monitor)) return false;
        Monitor monitor = (Monitor) o;
        return name.equals(monitor.name) &&
                type == monitor.type &&
                value.equals(monitor.value) &&
                Objects.equals(repeatTime, monitor.repeatTime) &&
                sockReady.equals(monitor.sockReady) &&
                activeMessage.equals(monitor.activeMessage) &&
                deactivateMessage.equals(monitor.deactivateMessage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type, value, repeatTime, sockReady, activeMessage, deactivateMessage);
    }

    @Override
    public String toString() {
        return "Monitor{" +
                "name='" + name + '\'' +
                ", type=" + type +
                ", value=" + value +
                ", repeatTime=" + repeatTime +
                ", activeMessage='" + activeMessage + '\'' +
                ", deactivateMessage='" + deactivateMessage + '\'' +
                '}';
    }
}
