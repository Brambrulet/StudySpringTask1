package ru.volnenko.se.command.task;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import ru.volnenko.se.api.command.InputPendingCommand;

/**
 * @author Denis Volnenko
 * @author Shmelev Dmitry
 */
@Component("task-remove")
@Setter(onMethod_=@Autowired)
public final class TaskRemoveCommand extends InputPendingCommand {

    @Override
    public String description() {
        return "Remove selected task.";
    }

    @Override
    public void prepare() {
        System.out.println("[REMOVING TASK]");
        System.out.println("Enter task order index:");
    }

    @Override
    public void execute(String inputValue) {
        if (stringToInteger(inputValue) == null) {
            System.out.println("Error! Incorrect order index...");
            System.out.println();
            return;
        }
        System.out.println("[OK]");
    }

    private Integer stringToInteger(String inputValue) {
        if (StringUtils.isEmpty(inputValue)) return null;
        try {
            return Integer.parseInt(inputValue);
        } catch (Exception e) {
            return null;
        }
    }
}
