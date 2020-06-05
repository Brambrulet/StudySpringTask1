package ru.volnenko.se.command.project;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.volnenko.se.api.service.IProjectService;
import ru.volnenko.se.command.AbstractCommand;
import ru.volnenko.se.entity.Project;

/**
 * @author Denis Volnenko
 */
@Service
@Setter(onMethod=@__({@Autowired}))
public final class ProjectListCommand implements AbstractCommand {
    private IProjectService projectService;

    @Override
    public String command() {
        return "project-list";
    }

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
