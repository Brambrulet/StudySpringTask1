package ru.volnenko.se.command;

/**
 * @author Denis Volnenko
 */
public interface AbstractCommand {

    void execute() throws Exception;

    String command();

    String description();
}
