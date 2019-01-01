package net.dpaulat.apps.ayla.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AylaGrant {

    @JsonProperty(value = "user-id")
    private Long userId;
    @JsonProperty(value = "start-date-at")
    private String startDateAt;
    @JsonProperty(value = "end-date-at")
    private String endDateAt;
    private String operation;

    public AylaGrant() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getStartDateAt() {
        return startDateAt;
    }

    public void setStartDateAt(String startDateAt) {
        this.startDateAt = startDateAt;
    }

    public String getEndDateAt() {
        return endDateAt;
    }

    public void setEndDateAt(String endDateAt) {
        this.endDateAt = endDateAt;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    @Override
    public String toString() {
        return "AylaGrant{" +
                "userId=" + userId +
                ", startDateAt='" + startDateAt + '\'' +
                ", endDateAt='" + endDateAt + '\'' +
                ", operation='" + operation + '\'' +
                '}';
    }
}
