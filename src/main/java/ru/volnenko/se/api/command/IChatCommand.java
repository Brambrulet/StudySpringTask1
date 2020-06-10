package ru.volnenko.se.api.command;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import ru.volnenko.se.api.component.IChatProvider;
import ru.volnenko.se.api.event.InputRequestEvent;
import ru.volnenko.se.api.event.InputResponseEvent;
import ru.volnenko.se.constant.DataConstant;
import ru.volnenko.se.error.CommandCorruptException;

public interface IChatCommand extends IInputPendingCommand {

    String BC = "bc";
    String PVP = "pvp";

    void sendMessage();
    void setLogin(String inputValue);
    void setBroadcastCoverage(boolean equals);
    void setMessage(String message);
    boolean isBroadcastCoverage();
    void setRecipient(String inputValue);
    IChatProvider getChatProvider();

    default void exit() {
        getChatProvider().stop();
    }

    @Override
    default void setBeanName(String command) {
        if (command.isEmpty()) {
            throw new CommandCorruptException();
        }
    }

    @Override
    default void execute(String login) {
        setLogin(login);
        composeMessage();
    }

    default void composeMessage() {
        System.out.println();
        System.out.println("new message.");
        System.out.println("Private or broadcast? (pvp/bc/exit):");
        getPublisher().publishEvent(new InputRequestEvent(this, getCommand() + "-coverage"));
    }

    @Async
    @EventListener(condition = "#command + '-coverage' eq #event.target")
    default void onInputCoverage(InputResponseEvent event) {
        switch (event.getInputValue()) {
            case DataConstant.EXIT:
                System.out.println("your connection with reality has intensified");
                exit();
                return;

            case BC:
                setBroadcastCoverage(true);
                break;

            case PVP:
                setBroadcastCoverage(false);
                break;

            default:
                System.out.println("invalid coverage");
                composeMessage();
                return;
        }
        System.out.println("message:");
        getPublisher().publishEvent(new InputRequestEvent(this, getCommand() + "-message"));
    }

    @Async
    @EventListener(condition = "#command + '-message' eq #event.target")
    default void onInputMessage(InputResponseEvent event) {
        setMessage(event.getInputValue());
        if (isBroadcastCoverage()) {
            sendMessage();
            composeMessage();
        } else {
            System.out.println("recipient:");
            getPublisher().publishEvent(new InputRequestEvent(this, getCommand() + "-recipient"));
        }
    }

    @Async
    @EventListener(condition = "#command + '-recipient' eq #event.target")
    default void onInputRecipient(InputResponseEvent event) {
        setRecipient(event.getInputValue());
        sendMessage();
        composeMessage();
    }
}
