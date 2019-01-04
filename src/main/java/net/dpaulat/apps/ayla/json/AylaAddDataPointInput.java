package net.dpaulat.apps.ayla.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class AylaAddDataPointInput {

    @NotNull
    private AylaAddDataPointInputDetail datapoint;

    public AylaAddDataPointInput() {
    }

    public AylaAddDataPointInput(String value) {
        datapoint = new AylaAddDataPointInputDetail(value);
    }

    public AylaAddDataPointInputDetail getDatapoint() {
        return datapoint;
    }

    public void setDatapoint(AylaAddDataPointInputDetail datapoint) {
        this.datapoint = datapoint;
    }

    @Override
    public String toString() {
        return "AylaAddDataPointInput{" +
                "datapoint=" + datapoint +
                '}';
    }
}
