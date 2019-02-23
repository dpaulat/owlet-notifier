package net.dpaulat.apps.alexa.api;

import java.util.HashMap;
import java.util.Map;

public interface ISkillMessage {

    String TYPE = "type";

    default Map<String, String> getData() {
        return new HashMap<>();
    }
}
