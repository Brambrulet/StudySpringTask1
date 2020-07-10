package ru.volnenko.se.command.project;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.volnenko.se.api.command.InputPendingCommand;
import ru.volnenko.se.api.service.IProjectService;

/**
 * @author Denis Volnenko
 * @author Shmelev Dmitry
 */
@Component("project-create")
@Setter(onMethod_=@Autowired)
public final class ProjectCreateCommand extends InputPendingCommand {

    //private IInputProvider input;
    private IProjectService projectService;

    @Override
    public String description() {
        return "Create new project.";
    }

    @Override
    public void prepare() {
        System.out.println("[PROJECT CREATE]");
        System.out.println("ENTER NAME:");
    }

    @Override
    public void execute(String name) {
        projectService.createProject(name);
        System.out.println("[OK]");
        System.out.println();
    }
}
