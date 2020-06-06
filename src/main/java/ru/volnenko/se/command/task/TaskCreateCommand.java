package ru.volnenko.se.command.task;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.volnenko.se.api.service.IConsoleService;
import ru.volnenko.se.command.AbstractCommand;
import ru.volnenko.se.repository.TaskRepository;

/**
 * @author Denis Volnenko
 */
@Service("task-create")
@Setter(onMethod=@__({@Autowired}))
public final class TaskCreateCommand implements AbstractCommand {
    private IConsoleService console;
    private TaskRepository taskRepository;

    @Override
    public String description() {
        return "Create new task.";
    }

    @Override
    public void execute() {
        System.out.println("[TASK CREATE]");
        System.out.println("ENTER NAME:");
        final String name = console.nextLine();
        taskRepository.createTask(name);
        System.out.println("[OK]");
        System.out.println();
    }

}
