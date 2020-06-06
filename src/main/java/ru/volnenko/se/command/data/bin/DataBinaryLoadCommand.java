package ru.volnenko.se.command.data.bin;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.volnenko.se.api.service.IProjectService;
import ru.volnenko.se.api.service.ITaskService;
import ru.volnenko.se.command.AbstractCommand;
import ru.volnenko.se.constant.DataConstant;
import ru.volnenko.se.entity.Project;
import ru.volnenko.se.entity.Task;

import java.io.FileInputStream;
import java.io.ObjectInputStream;

/**
 * @author Denis Volnenko
 */
@Service("data-bin-load")
@Setter(onMethod=@__({@Autowired}))
public final class DataBinaryLoadCommand implements AbstractCommand {
    private IProjectService projectService;
    private ITaskService taskService;

    @Override
    public String description() {
        return "Save data to binary file.";
    }

    @Override
    public void execute() throws Exception {
        System.out.println("[DATA BINARY LOAD]");
        final FileInputStream fileInputStream = new FileInputStream(DataConstant.FILE_BINARY);
        final ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        loadProjects(objectInputStream.readObject());
        loadTasks(objectInputStream.readObject());
        objectInputStream.close();
        fileInputStream.close();
        System.out.println("[OK]");
    }

    private void loadProjects(final Object value) {
        if (!(value instanceof Project[])) return;
        final Project[] projects = (Project[]) value;
        projectService.load(projects);
    }

    private void loadTasks(final Object value) {
        if (!(value instanceof Task[])) return;
        final Task[] tasks = (Task[]) value;
        taskService.load(tasks);
    }

}
