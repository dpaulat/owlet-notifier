package net.dpaulat.apps.owletnotifier.alexa.message;

import net.dpaulat.apps.alexa.api.ISkillMessage;

import java.util.HashMap;
import java.util.Map;

public class NotificationMessage implements ISkillMessage {

    public static final String MESSAGE = "message";

    private final Map<String, String> data;

    public NotificationMessage(String message) {
        data = new HashMap<>();
        data.put(MESSAGE, message);
    }

    public Map<String, String> getData() {
        return data;
    }
}
