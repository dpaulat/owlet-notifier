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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.TaskScheduler;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public abstract class RefreshTask implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(RefreshTask.class);

    private final @NotNull TaskScheduler taskScheduler;

    public RefreshTask(@NotNull TaskScheduler taskScheduler) {
        this.taskScheduler = taskScheduler;
    }

    public void scheduleTokenRefresh(long secondsFromNow) {
        Date date = Date.from(LocalDateTime.now().plusSeconds(secondsFromNow).atZone(ZoneId.systemDefault()).toInstant());

        taskScheduler.schedule(this, date);

        log.info("{} scheduled for {}", this.getClass().getSimpleName(), date);
    }
}
