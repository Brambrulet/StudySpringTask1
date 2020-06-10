package ru.volnenko.se.api.component;

import ru.volnenko.se.constant.DataConstant;

public interface IChatProvider {

    void start(String login);

    void stop();

    void sendMessage(String message);

    void sendMessage(String message, String recipient);

    default String getMessagePrefix(boolean broadcast) {
        return broadcast ? DataConstant.FOR_ALL : DataConstant.TO_YOU;
    }

    interface ThrowableAction {
        void execute() throws Throwable;
    }

    default void ignoringExceptions(ThrowableAction action) {
        try {
            action.execute();
        } catch (Throwable throwable) {
            //no act expected
        }
    }

}
