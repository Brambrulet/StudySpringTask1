package ru.volnenko.se.command.task;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.volnenko.se.api.repository.ITaskRepository;
import ru.volnenko.se.command.AbstractCommand;

/**
 * @author Denis Volnenko
 */
@Service
@Setter(onMethod=@__({@Autowired}))
public final class TaskClearCommand implements AbstractCommand {
    private ITaskRepository taskRepository;

    @Override
    public String description() {
        return "Remove all tasks.";
    }

    @Override
    public String command() {
        return "task-clear";
    }

    @Override
    public void execute() {
        taskRepository.clear();
        System.out.println("[ALL TASK REMOVED]");
    }

}
