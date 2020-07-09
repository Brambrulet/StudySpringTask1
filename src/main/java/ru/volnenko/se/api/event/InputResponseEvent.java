package ru.volnenko.se.api.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class InputResponseEvent extends ApplicationEvent {

    private final String target;

    private final String inputValue;

    public InputResponseEvent(Object source, String target, String inputValue) {
        super(source);
        this.target = target;
        this.inputValue = inputValue;
    }
}
