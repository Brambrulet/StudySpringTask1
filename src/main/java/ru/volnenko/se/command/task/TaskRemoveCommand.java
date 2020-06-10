package ru.volnenko.se.command.task;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.volnenko.se.api.component.AbstractCommand;
import ru.volnenko.se.api.component.AsyncAbstractCommand;
import ru.volnenko.se.api.component.IInputProvider;

/**
 * @author Denis Volnenko
 * @author Shmelev Dmitry
 */
@Component("task-remove")
@Setter(onMethod=@__({@Autowired}))
public final class TaskRemoveCommand implements AsyncAbstractCommand {

    private IInputProvider input;

    @Override
    public String description() {
        return "Remove selected task.";
    }

    @Override
    public void execute() {
        System.out.println("[REMOVING TASK]");
        System.out.println("Enter task order index:");
        final Integer orderIndex = input.nextInteger();
        if (orderIndex == null) {
            System.out.println("Error! Incorrect order index...");
            System.out.println();
            return;
        }
        System.out.println("[OK]");
    }

}
