package net.dpaulat.apps.owletnotifier.events;

import org.springframework.context.ApplicationEvent;

public class OwletEvent extends ApplicationEvent {

    private final String message;

    public OwletEvent(Object source, String message) {
        super(source);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
