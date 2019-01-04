package net.dpaulat.apps.ayla.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AylaAddDataPointOutput {

    private AylaAddDataPointOutputDetail datapoint;

    public AylaAddDataPointOutput() {
    }

    public AylaAddDataPointOutputDetail getDatapoint() {
        return datapoint;
    }

    public void setDatapoint(AylaAddDataPointOutputDetail datapoint) {
        this.datapoint = datapoint;
    }

    @Override
    public String toString() {
        return "AylaAddDataPointOutput{" +
                "datapoint=" + datapoint +
                '}';
    }
}
