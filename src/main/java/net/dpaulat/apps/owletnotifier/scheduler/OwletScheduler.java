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

import net.dpaulat.apps.ayla.json.AylaAuthorizationByEmail;
import net.dpaulat.apps.ayla.json.AylaDevice;
import net.dpaulat.apps.owlet.OwletApi;
import net.dpaulat.apps.owletnotifier.ConfigProperties;
import net.dpaulat.apps.owletnotifier.monitor.MonitorEvaluator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class OwletScheduler {

    private static final Logger log = LoggerFactory.getLogger(OwletScheduler.class);

    private final @NotNull ApplicationContext context;
    private final @NotNull ConfigProperties config;
    private final @NotNull OwletApi owletApi;
    private final @NotNull RefreshTokenTask refreshTokenTask;
    private final List<IOwletTask> periodicTaskList;
    private final Map<String, Map<IOwletTask, Long>> taskLastRunMap;
    private boolean initialized;
    private List<AylaDevice> deviceList;

    public OwletScheduler(@NotNull ApplicationContext context, @NotNull ConfigProperties config,
                          @NotNull MonitorEvaluator monitorEvaluator, @NotNull OwletApi owletApi,
                          @NotNull RefreshTokenTask refreshTokenTask) {
        this.context = context;
        this.config = config;
        this.owletApi = owletApi;
        this.refreshTokenTask = refreshTokenTask;
        this.initialized = false;
        this.deviceList = null;

        this.periodicTaskList = new ArrayList<>();
        periodicTaskList.add(new AppActivityTask(owletApi));
        periodicTaskList.add(new MonitorEvaluatorTask(config, owletApi, monitorEvaluator));

        this.taskLastRunMap = new HashMap<>();
    }

    @Scheduled(fixedRate = SchedulerTypes.FRAME_TIME)
    public void process() {
        if (!initialized) {
            initialize();
        } else {
            runOnce();
        }
    }

    private void initialize() {
        log.info("Initializing Owlet Monitor");
        log.debug(config.toString());

        AylaAuthorizationByEmail auth = owletApi.signIn(config.getOwlet().getEmail(), config.getOwlet().getPassword());
        if (!owletApi.isSignedIn()) {
            log.error("Could not sign in, exiting");
            SpringApplication.exit(context, () -> -1);
        }
        refreshTokenTask.scheduleTokenRefresh(auth.getExpiresIn() / 2);

        deviceList = owletApi.retrieveDevices();

        for (AylaDevice device : deviceList) {
            log.info("Found Owlet device: " + device.getDsn());
            owletApi.setAppActive(device);
            owletApi.updateProperties(device);
        }

        initialized = true;
    }

    private boolean periodHasElapsed(IOwletTask task, long taskLastRun) {
        final long epsilon = SchedulerTypes.FRAME_TIME / 2;
        return (taskLastRun + task.period() - epsilon <= System.currentTimeMillis());
    }

    private void runOnce() {
        for (AylaDevice device : deviceList) {
            for (IOwletTask task : periodicTaskList) {
                Map<IOwletTask, Long> deviceTaskLastRunMap = getDeviceTaskLastRunMap(device);
                Long taskLastRun = deviceTaskLastRunMap.get(task);
                if (taskLastRun == null || periodHasElapsed(task, taskLastRun)) {
                    // Log time task was started
                    taskLastRun = System.currentTimeMillis();
                    deviceTaskLastRunMap.put(task, taskLastRun);

                    // Run task
                    task.run(device);
                }
            }
        }
    }

    private Map<IOwletTask, Long> getDeviceTaskLastRunMap(AylaDevice device) {
        Map<IOwletTask, Long> deviceTaskLastRunMap;
        if (taskLastRunMap.containsKey(device.getDsn())) {
            deviceTaskLastRunMap = taskLastRunMap.get(device.getDsn());
        } else {
            deviceTaskLastRunMap = new HashMap<>();
            taskLastRunMap.put(device.getDsn(), deviceTaskLastRunMap);
        }
        return deviceTaskLastRunMap;
    }
}
