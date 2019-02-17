package net.dpaulat.apps.alexa.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.HashMap;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SkillMessageRequest {

    private final Map<String, String> data;
    private Long expiresAfterSeconds;

    public SkillMessageRequest() {
        this.data = new HashMap<>();
    }

    public Map<String, String> getData() {
        return data;
    }

    public Long getExpiresAfterSeconds() {
        return expiresAfterSeconds;
    }

    public void setExpiresAfterSeconds(Long expiresAfterSeconds) {
        this.expiresAfterSeconds = expiresAfterSeconds;
    }

    @Override
    public String toString() {
        return "SkillMessageRequest{" +
               "data=" + data +
               ", expiresAfterSeconds=" + expiresAfterSeconds +
               '}';
    }
}
