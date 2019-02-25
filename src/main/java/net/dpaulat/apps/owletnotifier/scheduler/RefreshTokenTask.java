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
import net.dpaulat.apps.owlet.OwletApi;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

@Component
public class RefreshTokenTask extends RefreshTask {

    private final @NotNull OwletApi owletApi;

    public RefreshTokenTask(@NotNull TaskScheduler taskScheduler, @NotNull OwletApi owletApi) {
        super(taskScheduler);

        this.owletApi = owletApi;
    }

    @Override
    public void run() {
        AylaAuthorizationByEmail auth = owletApi.refreshToken();
        scheduleTokenRefresh(auth.getExpiresIn() / 2);
    }
}
