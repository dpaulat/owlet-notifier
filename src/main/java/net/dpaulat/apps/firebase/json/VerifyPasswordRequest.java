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

import java.math.BigInteger;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VerifyPasswordRequest {

    private String captchaChallenge;
    private String captchaResponse;
    private Long delegatedProjectNumber;
    private String email;
    private String idToken;
    private String instanceId;
    private String pendingIdToken;
    private String password;
    private Boolean returnSecureToken;
    private String tenantId;
    private BigInteger tenantProjectNumber;

    public VerifyPasswordRequest() {
    }

    public String getCaptchaChallenge() {
        return captchaChallenge;
    }

    public void setCaptchaChallenge(String captchaChallenge) {
        this.captchaChallenge = captchaChallenge;
    }

    public String getCaptchaResponse() {
        return captchaResponse;
    }

    public void setCaptchaResponse(String captchaResponse) {
        this.captchaResponse = captchaResponse;
    }

    public Long getDelegatedProjectNumber() {
        return delegatedProjectNumber;
    }

    public void setDelegatedProjectNumber(Long delegatedProjectNumber) {
        this.delegatedProjectNumber = delegatedProjectNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getPendingIdToken() {
        return pendingIdToken;
    }

    public void setPendingIdToken(String pendingIdToken) {
        this.pendingIdToken = pendingIdToken;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getReturnSecureToken() {
        return returnSecureToken;
    }

    public void setReturnSecureToken(Boolean returnSecureToken) {
        this.returnSecureToken = returnSecureToken;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public BigInteger getTenantProjectNumber() {
        return tenantProjectNumber;
    }

    public void setTenantProjectNumber(BigInteger tenantProjectNumber) {
        this.tenantProjectNumber = tenantProjectNumber;
    }

    @Override
    public String toString() {
        return "VerifyPasswordRequest{" +
                "captchaChallenge='" + captchaChallenge + '\'' +
                ", captchaResponse='" + captchaResponse + '\'' +
                ", delegatedProjectNumber=" + delegatedProjectNumber +
                ", email='" + email + '\'' +
                ", idToken='" + idToken + '\'' +
                ", instanceId='" + instanceId + '\'' +
                ", pendingIdToken='" + pendingIdToken + '\'' +
                ", password='***'" +
                ", returnSecureToken=" + returnSecureToken +
                ", tenantId='" + tenantId + '\'' +
                ", tenantProjectNumber=" + tenantProjectNumber +
                '}';
    }
}
