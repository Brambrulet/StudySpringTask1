package ru.volnenko.se.command.task;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.volnenko.se.api.command.IRegularCommand;
import ru.volnenko.se.api.repository.ITaskRepository;

/**
 * @author Denis Volnenko
 * @author Shmelev Dmitry
 */
@Component("task-clear")
@Setter(onMethod_=@Autowired)
public final class TaskClearCommandI implements IRegularCommand {

    private ITaskRepository taskRepository;

    @Override
    public String description() {
        return "Remove all tasks.";
    }

    @Override
    public void execute() {
        taskRepository.clear();
        System.out.println("[ALL TASK REMOVED]");
    }

}
