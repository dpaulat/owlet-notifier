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

import java.util.Arrays;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AylaAuthorizationByEmail {

    private String code;
    @JsonProperty(value = "access_token")
    private String accessToken;
    @JsonProperty(value = "refresh_token")
    private String refreshToken;
    @JsonProperty(value = "expires_in")
    private Long expiresIn;
    private String role;
    @JsonProperty(value = "role_tags")
    private AylaRoleTag[] roleTags;

    public AylaAuthorizationByEmail() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public AylaRoleTag[] getRoleTags() {
        return roleTags;
    }

    public void setRoleTags(AylaRoleTag[] roleTags) {
        this.roleTags = roleTags;
    }

    @Override
    public String toString() {
        return "AylaAuthorizationByEmail{" +
               "code='" + code + '\'' +
               ", accessToken='" + accessToken + '\'' +
               ", refreshToken='" + refreshToken + '\'' +
               ", expiresIn=" + expiresIn +
               ", role='" + role + '\'' +
               ", roleTags=" + Arrays.toString(roleTags) +
               '}';
    }
}
