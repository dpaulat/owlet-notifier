package net.dpaulat.apps.alexa.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorResponse {

    private String error;

    @JsonProperty(value = "error_description")
    private String errorDescription;

    @JsonProperty(value = "error_uri")
    private String errorUri;

    private String state;

    public ErrorResponse() {
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    public String getErrorUri() {
        return errorUri;
    }

    public void setErrorUri(String errorUri) {
        this.errorUri = errorUri;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "ErrorResponse{" +
               "error='" + error + '\'' +
               ", errorDescription='" + errorDescription + '\'' +
               ", errorUri='" + errorUri + '\'' +
               ", state='" + state + '\'' +
               '}';
    }
}
