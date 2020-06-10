package ru.volnenko.se.command.system;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import ru.volnenko.se.api.command.ICommand;
import ru.volnenko.se.api.command.IRegularCommand;

/**
 * @author Denis Volnenko
 * @author Shmelev Dmitry
 */
@Component("help")
@Setter(onMethod_=@Autowired)
public class HelpCommandI implements IRegularCommand {

    private ApplicationContext context;

    @Override
    public String description() {
        return "Show all commands.";
    }

    @Override
    public void execute() {
        context.getBeansOfType(ICommand.class).forEach(this::printCommandInfo);
    }

    private void printCommandInfo(String command, ICommand bean) {
        System.out.println(command + ": " + bean.description());
    }

}
