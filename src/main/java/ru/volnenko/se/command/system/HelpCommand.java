package ru.volnenko.se.command.system;

import java.util.List;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.volnenko.se.command.AbstractCommand;

/**
 * @author Denis Volnenko
 */
@Service
@Setter(onMethod=@__({@Autowired}))
public final class HelpCommand implements AbstractCommand {
    private List<AbstractCommand> commands;

    @Override
    public String command() {
        return "help";
    }

    @Override
    public String description() {
        return "Show all commands.";
    }

    @Override
    public void execute() {
        for (AbstractCommand command: commands) {
            System.out.println(command.command()+ ": " + command.description());
        }
    }

}
