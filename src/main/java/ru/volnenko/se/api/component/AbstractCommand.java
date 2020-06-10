package ru.volnenko.se.api.component;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.event.EventListener;
import org.springframework.util.StringUtils;
import ru.volnenko.se.api.event.CommandEvent;
import ru.volnenko.se.error.CommandCorruptException;

/**
 * @author Denis Volnenko
 * @author Shmelev Dmitry
 */
public interface AbstractCommand extends BeanNameAware {

    void execute() throws Exception;

    String description();

    @PostConstruct
    default void validateCommand() {
        if (StringUtils.isEmpty(description())) {
            throw new CommandCorruptException();
        }
    }

    @EventListener(condition = "#command eq #event.command")
    default void onEvent(CommandEvent event) throws Exception {
        execute();
    }

    @Override
    default void setBeanName(String command) {
        if (command.isEmpty()) {
            throw new CommandCorruptException();
        }
    }
}
