package ru.volnenko.se.api.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class InputRequestEvent extends ApplicationEvent {
    private final String returnAddress;

    public InputRequestEvent(Object source, String returnAddress) {
        super(source);
        this.returnAddress = returnAddress;
    }
}
