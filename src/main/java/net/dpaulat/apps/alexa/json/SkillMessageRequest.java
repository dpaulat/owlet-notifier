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

package net.dpaulat.apps.alexa.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.HashMap;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SkillMessageRequest {

    private final Map<String, String> data;
    private Long expiresAfterSeconds;

    public SkillMessageRequest() {
        this.data = new HashMap<>();
    }

    public Map<String, String> getData() {
        return data;
    }

    public Long getExpiresAfterSeconds() {
        return expiresAfterSeconds;
    }

    public void setExpiresAfterSeconds(Long expiresAfterSeconds) {
        this.expiresAfterSeconds = expiresAfterSeconds;
    }

    @Override
    public String toString() {
        return "SkillMessageRequest{" +
               "data=" + data +
               ", expiresAfterSeconds=" + expiresAfterSeconds +
               '}';
    }
}
