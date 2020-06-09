package ru.volnenko.se.api.component;

import org.springframework.scheduling.annotation.Async;
import ru.volnenko.se.api.event.CommandEvent;

/**
 * @author Shmelev Dmitry
 */
public interface AsyncAbstractCommand extends AbstractCommand {

    @Async
    @Override
    default void onEvent(CommandEvent event) throws Exception {
        execute();
    }
}
