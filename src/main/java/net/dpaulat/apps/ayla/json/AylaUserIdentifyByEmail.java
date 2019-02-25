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

@JsonIgnoreProperties(ignoreUnknown = true)
public class AylaUserIdentifyByEmail {

    private String email;
    private String password;
    private AylaApplication application;

    public AylaUserIdentifyByEmail() {
    }

    public AylaUserIdentifyByEmail(String email, String password, AylaApplication application) {
        this.email = email;
        this.password = password;
        this.application = application;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public AylaApplication getApplication() {
        return application;
    }

    public void setApplication(AylaApplication application) {
        this.application = application;
    }

    @Override
    public String toString() {
        return "AylaUserIdentifyByEmail{" +
               "email='" + email + '\'' +
               ", password='***'" +
               ", application=" + application +
               '}';
    }
}
