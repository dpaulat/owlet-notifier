package net.dpaulat.apps.ayla.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class AylaAddDataPointInputDetail {

    @NotNull
    private String value;
    private AylaDataPointMetadata metadata;

    public AylaAddDataPointInputDetail() {
    }

    public AylaAddDataPointInputDetail(String value) {
        this.value = value;
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
        return "AylaAddDataPointInputDetail{" +
                "value='" + value + '\'' +
                ", metadata=" + metadata +
                '}';
    }
}
