package ru.volnenko.se.api.command;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import ru.volnenko.se.api.event.InputResponseEvent;

public interface IInputPendingCommand extends ICommand {

    void prepare();

    void execute(String inputValue);

    void setCommand(String command);
    String getCommand();
    ApplicationEventPublisher getPublisher();

    @Async
    @EventListener(condition = "#command eq #event.target")
    default void onInputValue(InputResponseEvent event) {
        setCommand(event.getTarget());
        execute(event.getInputValue());
    }
}
