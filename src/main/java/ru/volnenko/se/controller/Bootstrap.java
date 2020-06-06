package ru.volnenko.se.controller;

import java.util.LinkedHashMap;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.volnenko.se.api.service.IBootstrap;
import ru.volnenko.se.api.service.IConsoleService;
import ru.volnenko.se.command.AbstractCommand;
import ru.volnenko.se.error.CommandAbsentException;
import ru.volnenko.se.error.CommandCorruptException;


/**
 * @author Denis Volnenko
 */
@Controller
@Setter(onMethod=@__({@Autowired}))
public final class Bootstrap implements IBootstrap {
    @Getter
    private Map<String, AbstractCommand> commands;
    private IConsoleService consoleService;

    private void validateCommand(String commandString, final AbstractCommand command) {
        final String cliDescription = command.description();
        if (commandString == null || commandString.isEmpty()) throw new CommandCorruptException();
        if (cliDescription == null || cliDescription.isEmpty()) throw new CommandCorruptException();
    }

    @Override
    public void start() throws Exception {
        if (commands.isEmpty()) throw new CommandAbsentException();
        commands.forEach(this::validateCommand);

        System.out.println("*** WELCOME TO TASK MANAGER ***");
        String command = "";
        while (!"exit".equals(command)) {
            command = consoleService.nextLine();
            execute(command);
        }
    }

    private void execute(final String command) throws Exception {
        if (command == null || command.isEmpty()) return;
        final AbstractCommand abstractCommand = commands.get(command);
        if (abstractCommand == null) return;
        abstractCommand.execute();
    }

}
