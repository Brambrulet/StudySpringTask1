package ru.volnenko.se.api.component;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import ru.volnenko.se.api.event.InputRequestEvent;

/**
 * @author Shmelev Dmitry
 */
public interface IInputProvider {

    void start();

    @Async
    @EventListener
    void onInputRequest(InputRequestEvent event);
}
