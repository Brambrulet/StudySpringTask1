package ru.volnenko.se.command.task;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.volnenko.se.api.service.IConsoleService;
import ru.volnenko.se.command.AbstractCommand;

/**
 * @author Denis Volnenko
 */
@Service("task-remove")
@Setter(onMethod=@__({@Autowired}))
public final class TaskRemoveCommand implements AbstractCommand {
    private IConsoleService console;

    @Override
    public String description() {
        return "Remove selected task.";
    }

    @Override
    public void execute() {
        System.out.println("[REMOVING TASK]");
        System.out.println("Enter task order index:");
        final Integer orderIndex = console.nextInteger();
        if (orderIndex == null) {
            System.out.println("Error! Incorrect order index...");
            System.out.println();
            return;
        }
        System.out.println("[OK]");
    }

}
