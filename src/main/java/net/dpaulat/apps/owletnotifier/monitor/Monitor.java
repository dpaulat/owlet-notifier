/*
 * Copyright 2019 Dan Paulat
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.dpaulat.apps.owletnotifier.monitor;

import net.dpaulat.apps.owlet.OwletProperties;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

public class Monitor {
    private final ConditionStatus status = new ConditionStatus();
    @NotNull
    private OwletProperties property;
    @NotNull
    private ConditionType type;
    @NotNull
    private Integer value;
    private Long repeatTime;
    @NotNull
    private Boolean sockReady = false;
    @NotBlank
    private String activeMessage = "%1$s's %2$s is %3$s";
    @NotBlank
    private String deactivateMessage = "%1$s's %2$s is %3$s";

    public ConditionStatus getStatus() {
        return status;
    }

    public OwletProperties getProperty() {
        return property;
    }

    public void setProperty(OwletProperties property) {
        this.property = property;
    }

    public ConditionType getType() {
        return type;
    }

    public void setType(ConditionType type) {
        this.type = type;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
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
        return status.equals(monitor.status) &&
               property.equals(monitor.property) &&
               type == monitor.type &&
               value.equals(monitor.value) &&
               Objects.equals(repeatTime, monitor.repeatTime) &&
               sockReady.equals(monitor.sockReady) &&
               activeMessage.equals(monitor.activeMessage) &&
               deactivateMessage.equals(monitor.deactivateMessage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(status, property, type, value, repeatTime, sockReady, activeMessage, deactivateMessage);
    }

    @Override
    public String toString() {
        return "Monitor{" +
               "status=" + status +
               ", property='" + property + '\'' +
               ", type=" + type +
               ", value=" + value +
               ", repeatTime=" + repeatTime +
               ", sockReady=" + sockReady +
               ", activeMessage='" + activeMessage + '\'' +
               ", deactivateMessage='" + deactivateMessage + '\'' +
               '}';
    }
}
