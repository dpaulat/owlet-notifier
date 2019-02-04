package net.dpaulat.apps.alexa.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthorizationResponse {

    @NotNull
    private String code;

    private String state;

    public AuthorizationResponse() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "AuthorizationResponse{" +
               "code='" + code + '\'' +
               ", state='" + state + '\'' +
               '}';
    }
}
