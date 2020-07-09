package ru.volnenko.se.api.command;

import lombok.Data;

@Data
public abstract class ChatCommand extends InputPendingCommand implements IChatCommand {

    private String login;
    private String recipient;
    private String message;
    private boolean broadcastCoverage;

    @Override
    public void setLogin(String login) {
        this.login = login;
        getChatProvider().start(login);
    }

    @Override
    public void sendMessage() {
        if (isBroadcastCoverage()) {
            getChatProvider().sendMessage(message);
        } else {
            getChatProvider().sendMessage(message, recipient);
        }
    }
}
