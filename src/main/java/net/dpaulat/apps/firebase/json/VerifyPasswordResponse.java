/*
 * Copyright 2021 Dan Paulat
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

package net.dpaulat.apps.firebase.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VerifyPasswordResponse {

    private String displayName;
    private String email;
    private Long expiresIn;
    private String idToken;
    private String kind;
    private String localId;
    private String oauthAccessToken;
    private String oauthAuthorizationCode;
    private Integer oauthExpireIn;
    private String photoUrl;
    private String refreshToken;
    private Boolean registered;

    public VerifyPasswordResponse() {
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getLocalId() {
        return localId;
    }

    public void setLocalId(String localId) {
        this.localId = localId;
    }

    public String getOauthAccessToken() {
        return oauthAccessToken;
    }

    public void setOauthAccessToken(String oauthAccessToken) {
        this.oauthAccessToken = oauthAccessToken;
    }

    public String getOauthAuthorizationCode() {
        return oauthAuthorizationCode;
    }

    public void setOauthAuthorizationCode(String oauthAuthorizationCode) {
        this.oauthAuthorizationCode = oauthAuthorizationCode;
    }

    public Integer getOauthExpireIn() {
        return oauthExpireIn;
    }

    public void setOauthExpireIn(Integer oauthExpireIn) {
        this.oauthExpireIn = oauthExpireIn;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Boolean getRegistered() {
        return registered;
    }

    public void setRegistered(Boolean registered) {
        this.registered = registered;
    }

    @Override
    public String toString() {
        return "VerifyPasswordResponse{" +
                "displayName='" + displayName + '\'' +
                ", email='" + email + '\'' +
                ", expiresIn=" + expiresIn +
                ", idToken='" + idToken + '\'' +
                ", kind='" + kind + '\'' +
                ", localId='" + localId + '\'' +
                ", oauthAccessToken='" + oauthAccessToken + '\'' +
                ", oauthAuthorizationCode='" + oauthAuthorizationCode + '\'' +
                ", oauthExpireIn=" + oauthExpireIn +
                ", photoUrl='" + photoUrl + '\'' +
                ", refreshToken='" + refreshToken + '\'' +
                ", registered=" + registered +
                '}';
    }
}
