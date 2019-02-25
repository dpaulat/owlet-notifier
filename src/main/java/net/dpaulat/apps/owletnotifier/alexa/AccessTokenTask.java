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

package net.dpaulat.apps.owletnotifier.alexa;

import net.dpaulat.apps.alexa.api.AlexaApi;
import net.dpaulat.apps.alexa.json.AccessTokenResponse;
import net.dpaulat.apps.owletnotifier.scheduler.RefreshTask;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

@Component
public class AccessTokenTask extends RefreshTask {

    private final @NotNull AlexaApi alexaApi;

    public AccessTokenTask(@NotNull TaskScheduler taskScheduler, @NotNull AlexaApi alexaApi) {
        super(taskScheduler);

        this.alexaApi = alexaApi;
    }

    @Override
    public void run() {
        AccessTokenResponse accessToken = alexaApi.requestAccessToken();
        if (accessToken != null) {
            scheduleTokenRefresh(accessToken.getExpiresIn() / 2);
        } else {
            scheduleTokenRefresh(60);
        }
    }
}
