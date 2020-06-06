package ru.volnenko.se.command.system;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.volnenko.se.api.service.IBootstrap;
import ru.volnenko.se.command.AbstractCommand;

/**
 * @author Denis Volnenko
 */
@Service("help")
@Setter(onMethod=@__({@Autowired}))
public final class HelpCommand implements AbstractCommand {
    private IBootstrap bootstrap;

    @Override
    public String description() {
        return "Show all commands.";
    }

    @Override
    public void execute() {
        bootstrap.getCommands().forEach(this::printCommandInfo);
    }

    private void printCommandInfo(String key, AbstractCommand command) {
        System.out.println(key + ": " + command.description());
    }

}
