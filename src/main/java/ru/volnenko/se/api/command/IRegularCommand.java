package ru.volnenko.se.api.command;

import ru.volnenko.se.api.event.CommandEvent;

public interface IRegularCommand extends ICommand {

    void execute() throws Exception;

    @Override
    default void onCommand(CommandEvent event) throws Exception {
        execute();
    }

}
