package net.dpaulat.apps.ayla.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AylaDevPropertyWrapper {

    private AylaDevProperty property;

    public AylaDevPropertyWrapper() {
    }

    public AylaDevProperty getProperty() {
        return property;
    }

    public void setProperty(AylaDevProperty property) {
        this.property = property;
    }

    @Override
    public String toString() {
        return "AylaDevPropertyWrapper{" +
                "property=" + property +
                '}';
    }
}
