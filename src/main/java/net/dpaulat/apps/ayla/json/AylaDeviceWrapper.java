package net.dpaulat.apps.ayla.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AylaDeviceWrapper {

    private AylaDevice device;

    public AylaDeviceWrapper() {
    }

    public AylaDevice getDevice() {
        return device;
    }

    public void setDevice(AylaDevice device) {
        this.device = device;
    }

    @Override
    public String toString() {
        return "AylaDeviceWrapper{" +
                "device=" + device +
                '}';
    }
}
