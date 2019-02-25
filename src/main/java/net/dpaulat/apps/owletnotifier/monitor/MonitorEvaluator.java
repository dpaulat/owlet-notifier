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

import net.dpaulat.apps.ayla.json.AylaDevice;
import net.dpaulat.apps.owlet.OwletApi;
import net.dpaulat.apps.owlet.OwletProperties;
import net.dpaulat.apps.owletnotifier.events.OwletEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

@Service
public class MonitorEvaluator {

    private static final Logger log = LoggerFactory.getLogger(MonitorEvaluator.class);

    private final @NotNull ApplicationEventPublisher applicationEventPublisher;
    private final @NotNull OwletApi owletApi;

    public MonitorEvaluator(@NotNull ApplicationEventPublisher applicationEventPublisher, @NotNull OwletApi owletApi) {
        this.applicationEventPublisher = applicationEventPublisher;
        this.owletApi = owletApi;
    }

    public void evaluate(AylaDevice device, Monitor monitor) {
        OwletProperties property = monitor.getProperty();
        final Integer value = owletApi.getPropertyValue(device, property, Integer.class);

        log.debug("Evaluating {} [{}]: {}", property.getDisplayName(), device.getDsn(), value);

        if (value != null && !monitor.getSockReady() || owletApi.isSockReady(device)) {
            if (monitor.getType().getCondition().isConditionActive(monitor, value)) {
                String activeMessage = String.format(monitor.getActiveMessage(),
                        owletApi.getPropertyValue(device, OwletProperties.BABY_NAME, String.class),
                        property.getDisplayName().toLowerCase(), value);

                log.warn(activeMessage);

                if (!monitor.getStatus().isActive() || monitor.getStatus().readyToRepeat(monitor.getRepeatTime())) {
                    monitor.getStatus().activate();
                    applicationEventPublisher.publishEvent(new OwletEvent(this, activeMessage));
                }
            } else if (monitor.getStatus().isActive()) {
                String deactivateMessage = String.format(monitor.getDeactivateMessage(),
                        owletApi.getPropertyValue(device, OwletProperties.BABY_NAME, String.class),
                        property.getDisplayName().toLowerCase(), value);

                log.info(deactivateMessage);
                monitor.getStatus().deactivate();
                applicationEventPublisher.publishEvent(new OwletEvent(this, deactivateMessage));
            }
        }
    }
}
