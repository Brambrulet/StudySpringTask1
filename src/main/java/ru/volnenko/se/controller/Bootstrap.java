package ru.volnenko.se.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
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
public final class Bootstrap implements IBootstrap {
    private final Map<String, AbstractCommand> commands = new LinkedHashMap<>();
    private IConsoleService consoleService;

    @Autowired
    void setCommands(List<AbstractCommand> commands) {
        this.commands.putAll(commands.stream().collect(Collectors.toMap(AbstractCommand::command, this::validateCommand)));
    }

    @Autowired
    public void setConsoleService(IConsoleService consoleService) {
        this.consoleService = consoleService;
    }

    private AbstractCommand validateCommand(final AbstractCommand command) {
        final String cliCommand = command.command();
        final String cliDescription = command.description();
        if (cliCommand == null || cliCommand.isEmpty()) throw new CommandCorruptException();
        if (cliDescription == null || cliDescription.isEmpty()) throw new CommandCorruptException();

        return command;
    }

    @Override
    public void start() throws Exception {
        if (commands.isEmpty()) throw new CommandAbsentException();

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
