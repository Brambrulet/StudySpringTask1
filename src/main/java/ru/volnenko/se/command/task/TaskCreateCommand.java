package ru.volnenko.se.command.task;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.volnenko.se.api.component.IInputProvider;
import ru.volnenko.se.api.component.AbstractCommand;
import ru.volnenko.se.repository.TaskRepository;

/**
 * @author Denis Volnenko
 * @author Shmelev Dmitry
 */
@Component("task-create")
@Setter(onMethod=@__({@Autowired}))
public final class TaskCreateCommand implements AbstractCommand {

    private IInputProvider input;
    private TaskRepository taskRepository;

    @Override
    public String description() {
        return "Create new task.";
    }

    @Override
    public void execute() {
        System.out.println("[TASK CREATE]");
        System.out.println("ENTER NAME:");
        final String name = input.nextLine();
        taskRepository.createTask(name);
        System.out.println("[OK]");
        System.out.println();
    }

}
