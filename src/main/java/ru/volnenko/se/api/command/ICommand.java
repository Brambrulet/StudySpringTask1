package ru.volnenko.se.api.command;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.util.StringUtils;
import ru.volnenko.se.api.event.CommandEvent;
import ru.volnenko.se.error.CommandCorruptException;

/**
 * @author Denis Volnenko
 * @author Shmelev Dmitry
 */
public interface ICommand extends BeanNameAware {

    String description();

    @Async
    @EventListener(condition = "#command eq #event.command")
    void onCommand(CommandEvent event) throws Exception ;

    @PostConstruct
    default void validateCommand() {
        if (StringUtils.isEmpty(description())) {
            throw new CommandCorruptException();
        }
    }

    @Override
    default void setBeanName(String command) {
        if (command.isEmpty()) {
            throw new CommandCorruptException();
        }
    }
}
