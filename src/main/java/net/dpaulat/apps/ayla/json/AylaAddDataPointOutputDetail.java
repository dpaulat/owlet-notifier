package net.dpaulat.apps.ayla.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AylaAddDataPointOutputDetail {

    @JsonProperty(value = "created_at")
    private String createdAt;
    @JsonProperty(value = "updated_at")
    private String updatedAt;
    private Boolean echo;
    private String value;
    private AylaDataPointMetadata metadata;

    public AylaAddDataPointOutputDetail() {
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Boolean getEcho() {
        return echo;
    }

    public void setEcho(Boolean echo) {
        this.echo = echo;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public AylaDataPointMetadata getMetadata() {
        return metadata;
    }

    public void setMetadata(AylaDataPointMetadata metadata) {
        this.metadata = metadata;
    }

    @Override
    public String toString() {
        return "AylaAddDataPointOutputDetail{" +
                "createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                ", echo=" + echo +
                ", value='" + value + '\'' +
                ", metadata=" + metadata +
                '}';
    }
}
