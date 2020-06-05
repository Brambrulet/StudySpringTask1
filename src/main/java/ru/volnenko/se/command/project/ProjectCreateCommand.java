package ru.volnenko.se.command.project;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.volnenko.se.api.repository.IProjectRepository;
import ru.volnenko.se.api.service.IConsoleService;
import ru.volnenko.se.command.AbstractCommand;

/**
 * @author Denis Volnenko
 */
@Service
@Setter(onMethod=@__({@Autowired}))
public final class ProjectCreateCommand implements AbstractCommand {
    private IConsoleService console;
    private IProjectRepository projectRepository;

    @Override
    public String description() {
        return "Create new project.";
    }

    @Override
    public String command() {
        return "project-create";
    }

    @Override
    public void execute() {
        System.out.println("[PROJECT CREATE]");
        System.out.println("ENTER NAME:");
        final String name = console.nextLine();
        projectRepository.createProject(name);
        System.out.println("[OK]");
        System.out.println();
    }

}
