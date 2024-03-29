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
public class AylaTokenLoginInput {

    @JsonProperty(value = "app_id")
    private String appId;
    @JsonProperty(value = "app_secret")
    private String appSecret;
    private String provider;
    private String token;

    public AylaTokenLoginInput() {
    }

    public AylaTokenLoginInput(String appId, String appSecret, String provider, String token) {
        this.appId = appId;
        this.appSecret = appSecret;
        this.provider = provider;
        this.token = token;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "AylaTokenLoginInput{" +
                "appId='" + appId + '\'' +
                ", appSecret='" + appSecret + '\'' +
                ", provider='" + provider + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
