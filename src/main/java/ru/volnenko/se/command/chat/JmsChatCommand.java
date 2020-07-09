package ru.volnenko.se.command.chat;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.volnenko.se.api.command.ChatCommand;
import ru.volnenko.se.api.component.IChatProvider;

@Component("chat-jms")
public class JmsChatCommand extends ChatCommand {
    @Getter
    @Setter(onMethod_={@Autowired, @Qualifier("JmsChatProvider")})
    private IChatProvider chatProvider;

    @Override
    public void prepare() {
        System.out.println("[JMS brings together]");
    }

    @Override
    public String description() {
        return "chat via jms";
    }
}
