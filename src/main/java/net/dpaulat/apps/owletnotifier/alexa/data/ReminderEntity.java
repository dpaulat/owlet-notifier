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

package net.dpaulat.apps.owletnotifier.alexa.data;

import javax.persistence.*;

@Entity
public class ReminderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String userId;
    @Column(unique = true)
    private String deviceId;
    @Column(unique = true)
    private String alertToken;

    protected ReminderEntity() {
    }

    public ReminderEntity(String userId, String deviceId, String alertToken) {
        this.userId = userId;
        this.deviceId = deviceId;
        this.alertToken = alertToken;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getAlertToken() {
        return alertToken;
    }

    public void setAlertToken(String alertToken) {
        this.alertToken = alertToken;
    }

    @Override
    public String toString() {
        return "ReminderEntity{" +
               "id=" + id +
               ", userId='" + userId + '\'' +
               ", deviceId='" + deviceId + '\'' +
               ", alertToken='" + alertToken + '\'' +
               '}';
    }
}
