package ru.volnenko.se.command.project;

import org.springframework.stereotype.Service;
import ru.volnenko.se.command.AbstractCommand;

/**
 * @author Denis Volnenko
 */
@Service("project-remove")
public final class ProjectRemoveCommand implements AbstractCommand {

    @Override
    public String description() {
        return "Remove selected project.";
    }

    @Override
    public void execute() {

    }

}
