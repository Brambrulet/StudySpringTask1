package ru.volnenko.se.api.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * @author Shmelev Dmitry
 */
@Getter
public class CommandEvent extends ApplicationEvent {
    private final String command;

    public CommandEvent(Object source, String command) {
        super(source);
        this.command = command;
    }
}
