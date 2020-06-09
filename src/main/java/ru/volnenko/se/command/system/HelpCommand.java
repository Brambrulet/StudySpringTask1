package ru.volnenko.se.command.system;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import ru.volnenko.se.api.component.AbstractCommand;
import ru.volnenko.se.api.component.AsyncAbstractCommand;

/**
 * @author Denis Volnenko
 * @author Shmelev Dmitry
 */
@Component("help")
@Setter(onMethod=@__({@Autowired}))
public class HelpCommand implements AsyncAbstractCommand {

    private ApplicationContext context;

    @Override
    public String description() {
        return "Show all commands.";
    }

    @Override
    public void execute() {
        System.out.println(Thread.currentThread().getName());
        context.getBeansOfType(AbstractCommand.class).forEach(this::printCommandInfo);
    }

    private void printCommandInfo(String command, AbstractCommand bean) {
        System.out.println(command + ": " + bean.description());
    }

}
