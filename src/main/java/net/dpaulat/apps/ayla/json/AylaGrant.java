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

package net.dpaulat.apps.ayla.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AylaGrant {

    @JsonProperty(value = "user-id")
    private Long userId;
    @JsonProperty(value = "start-date-at")
    private String startDateAt;
    @JsonProperty(value = "end-date-at")
    private String endDateAt;
    private String operation;

    public AylaGrant() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getStartDateAt() {
        return startDateAt;
    }

    public void setStartDateAt(String startDateAt) {
        this.startDateAt = startDateAt;
    }

    public String getEndDateAt() {
        return endDateAt;
    }

    public void setEndDateAt(String endDateAt) {
        this.endDateAt = endDateAt;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    @Override
    public String toString() {
        return "AylaGrant{" +
               "userId=" + userId +
               ", startDateAt='" + startDateAt + '\'' +
               ", endDateAt='" + endDateAt + '\'' +
               ", operation='" + operation + '\'' +
               '}';
    }
}
