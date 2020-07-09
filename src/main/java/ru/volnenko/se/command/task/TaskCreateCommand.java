package ru.volnenko.se.command.task;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.volnenko.se.api.command.InputPendingCommand;
import ru.volnenko.se.repository.TaskRepository;

/**
 * @author Denis Volnenko
 * @author Shmelev Dmitry
 */
@Component("task-create")
@Setter(onMethod_=@Autowired)
public final class TaskCreateCommand extends InputPendingCommand {

    private TaskRepository taskRepository;

    @Override
    public String description() {
        return "Create new task.";
    }

    @Override
    public void prepare() {
        System.out.println("[TASK CREATE]");
        System.out.println("ENTER NAME:");
    }

    @Override
    public void execute(String name) {
        taskRepository.createTask(name);
        System.out.println("[OK]");
        System.out.println();
    }
}
