package ru.volnenko.se.command.project;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.volnenko.se.api.repository.IProjectRepository;
import ru.volnenko.se.api.component.IInputProvider;
import ru.volnenko.se.api.component.AbstractCommand;

/**
 * @author Denis Volnenko
 * @author Shmelev Dmitry
 */
@Component("project-create")
@Setter(onMethod=@__({@Autowired}))
public final class ProjectCreateCommand implements AbstractCommand {

    private IInputProvider input;
    private IProjectRepository projectRepository;

    @Override
    public String description() {
        return "Create new project.";
    }

    @Override
    public void execute() {
        System.out.println("[PROJECT CREATE]");
        System.out.println("ENTER NAME:");
        final String name = input.nextLine();
        projectRepository.createProject(name);
        System.out.println("[OK]");
        System.out.println();
    }

}
