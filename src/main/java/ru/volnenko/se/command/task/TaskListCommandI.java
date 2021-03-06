package ru.volnenko.se.command.task;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.volnenko.se.api.command.IRegularCommand;
import ru.volnenko.se.api.repository.ITaskRepository;
import ru.volnenko.se.entity.Task;

/**
 * @author Denis Volnenko
 * @author Shmelev Dmitry
 */
@Component("task-list")
@Setter(onMethod_=@Autowired)
public final class TaskListCommandI implements IRegularCommand {

    private ITaskRepository taskRepository;

    @Override
    public String description() {
        return "Show all tasks.";
    }

    @Override
    public void execute() {
        System.out.println("[TASK LIST]");
        int index = 1;
        for (Task task: taskRepository.getListTask()) {
            System.out.println(index + ". " + task.getName());
            index++;
        }
        System.out.println();
    }

}
