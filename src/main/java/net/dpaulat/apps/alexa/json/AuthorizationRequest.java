package net.dpaulat.apps.alexa.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthorizationRequest {

    @JsonProperty(value = "client_id")
    @NotNull
    private String clientId;

    @NotNull
    private String scope;

    @JsonProperty(value = "response_type")
    @NotNull
    private String responseType;

    @JsonProperty(value = "redirect_uri")
    @NotNull
    private String redirectUri;

    private String state;

    public AuthorizationRequest() {
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getResponseType() {
        return responseType;
    }

    public void setResponseType(String responseType) {
        this.responseType = responseType;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "AuthorizationRequest{" +
               "clientId='" + clientId + '\'' +
               ", scope='" + scope + '\'' +
               ", responseType='" + responseType + '\'' +
               ", redirectUri='" + redirectUri + '\'' +
               ", state='" + state + '\'' +
               '}';
    }
}
