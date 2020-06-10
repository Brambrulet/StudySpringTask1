package ru.volnenko.se.controller;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import ru.volnenko.se.api.service.IBootstrap;
import ru.volnenko.se.api.component.IInputProvider;
import ru.volnenko.se.api.event.CommandEvent;


/**
 * @author Denis Volnenko
 * @author Shmelev Dmitry
 */
@Component
@Setter(onMethod=@__({@Autowired}))
public final class Bootstrap implements IBootstrap {

    private static final String EXIT_COMMAND = "exit";
    private IInputProvider input;
    private ApplicationEventPublisher publisher;

    @Override
    public void start() {
        System.out.println("*** WELCOME TO TASK MANAGER ***");

        String command = "";
        while (!EXIT_COMMAND.equals(command)) {
            command = input.nextLine();
            if (!StringUtils.isEmpty(command)) {
                publisher.publishEvent(new CommandEvent(this, command));
            }
        }
    }

}
