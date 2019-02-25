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

package net.dpaulat.apps.owletnotifier.scheduler;

import net.dpaulat.apps.ayla.json.AylaDevice;
import net.dpaulat.apps.owlet.OwletApi;
import net.dpaulat.apps.owlet.OwletProperties;
import net.dpaulat.apps.owletnotifier.ConfigProperties;
import net.dpaulat.apps.owletnotifier.monitor.Monitor;
import net.dpaulat.apps.owletnotifier.monitor.MonitorEvaluator;

public class MonitorEvaluatorTask implements IOwletTask {

    private final ConfigProperties config;
    private final OwletApi owletApi;
    private final MonitorEvaluator monitorEvaluator;

    private Integer disableCounter = 0;

    public MonitorEvaluatorTask(ConfigProperties config, OwletApi owletApi, MonitorEvaluator monitorEvaluator) {

        this.config = config;
        this.owletApi = owletApi;
        this.monitorEvaluator = monitorEvaluator;
    }

    public long period() {
        return SchedulerTypes.Seconds.TEN_SECONDS;
    }

    @Override
    public void run(AylaDevice device) {
        if (owletApi.isMonitoringEnabled(device)) {
            owletApi.updateProperties(device);
            for (Monitor monitor : config.getOwlet().getMonitors()) {
                monitorEvaluator.evaluate(device, monitor);
            }
            evaluateMonitoringConditions(device);
        }
    }

    private void evaluateMonitoringConditions(AylaDevice device) {
        Boolean baseStationOn = owletApi.getPropertyValue(device, OwletProperties.BASE_STATION_ON, Boolean.class);
        Integer chargeStatus = owletApi.getPropertyValue(device, OwletProperties.CHARGE_STATUS, Integer.class);

        // If the base station is turned off, or the sock is plugged in, turn off monitoring
        if ((baseStationOn != null && !baseStationOn) ||
            (chargeStatus != null && chargeStatus > 0)) {
            disableCounter++;
        } else {
            disableCounter = 0;
        }

        if (disableCounter > 5) {
            owletApi.setMonitoringEnabled(device, false);
            disableCounter = 0;
        }
    }
}
