package ru.volnenko.se.api.command;

import lombok.Data;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import ru.volnenko.se.api.event.CommandEvent;
import ru.volnenko.se.api.event.InputRequestEvent;

@Data
public abstract class InputPendingCommand implements IInputPendingCommand {

    @Setter(onMethod_=@Autowired)
    private ApplicationEventPublisher publisher;

    private String command;

    /**
     * Почему-то не работает вот такой вариант:
     *    public ApplicationEvent onCommand(CommandEvent event) {
     *         prepare();
     *         return new InputRequestEvent(this, event.getCommand());
     *     }
     */
    @Override
    public void onCommand(CommandEvent event) {
        prepare();
        System.out.println("login:");
        publisher.publishEvent(new InputRequestEvent(this, event.getCommand()));
    }
}
