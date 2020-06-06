package ru.volnenko.se.api.service;

import java.util.Map;
import ru.volnenko.se.command.AbstractCommand;

public interface IBootstrap {

    void start() throws Exception;

    Map<String, AbstractCommand> getCommands();

}
