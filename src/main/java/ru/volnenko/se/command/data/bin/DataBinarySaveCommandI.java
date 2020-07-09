package ru.volnenko.se.command.data.bin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.volnenko.se.api.command.IRegularCommand;
import ru.volnenko.se.api.service.IProjectService;
import ru.volnenko.se.api.service.ITaskService;
import ru.volnenko.se.constant.DataConstant;
import ru.volnenko.se.entity.Project;
import ru.volnenko.se.entity.Task;

/**
 * @author Denis Volnenko
 * @author Shmelev Dmitry
 */
@Component("data-bin-save")
@Setter(onMethod_=@Autowired)
public final class DataBinarySaveCommandI implements IRegularCommand {

    private IProjectService projectService;
    private ITaskService taskService;

    @Override
    public String description() {
        return "Load data from binary file.";
    }

    @Override
    public void execute() throws Exception {
        System.out.println("[DATA BINARY SAVE]");
        final Project[] projects = projectService.getListProject().toArray(new Project[] {});
        final Task[] tasks = taskService.getListTask().toArray(new Task[] {});

        final File file = new File(DataConstant.FILE_BINARY);
        Files.deleteIfExists(file.toPath());
        Files.createFile(file.toPath());

        final FileOutputStream fileOutputStream = new FileOutputStream(file);
        final ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(projects);
        objectOutputStream.writeObject(tasks);
        objectOutputStream.close();
        fileOutputStream.close();

        System.out.println("[OK]");
        System.out.println();
    }

}
