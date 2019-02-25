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
public class AylaUserLoginInput {

    private AylaUserIdentifyByEmail user;

    public AylaUserLoginInput() {
    }

    public AylaUserLoginInput(String email, String password, AylaApplication application) {
        this.user = new AylaUserIdentifyByEmail(email, password, application);
    }

    public AylaUserIdentifyByEmail getUser() {
        return user;
    }

    public void setUser(AylaUserIdentifyByEmail user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "AylaUserLoginInput{" +
               "user=" + user +
               '}';
    }
}
