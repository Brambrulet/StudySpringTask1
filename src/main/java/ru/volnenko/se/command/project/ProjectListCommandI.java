package ru.volnenko.se.command.project;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.volnenko.se.api.command.IRegularCommand;
import ru.volnenko.se.api.service.IProjectService;
import ru.volnenko.se.entity.Project;

/**
 * @author Denis Volnenko
 * @author Shmelev Dmitry
 */
@Component("project-list")
@Setter(onMethod_=@Autowired)
public final class ProjectListCommandI implements IRegularCommand {

    private IProjectService projectService;

    @Override
    public String description() {
        return "Show all projects.";
    }

    @Override
    public void execute() {
        System.out.println("[PROJECT LIST]");
        int index = 1;
        for (Project project: projectService.getListProject()) {
            System.out.println(index++ + ". " + project.getName());
        }
        System.out.println();
    }

}
