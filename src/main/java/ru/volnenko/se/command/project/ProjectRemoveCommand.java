package ru.volnenko.se.command.project;

import org.springframework.stereotype.Component;
import ru.volnenko.se.api.component.AsyncAbstractCommand;

/**
 * @author Denis Volnenko
 * @author Shmelev Dmitry
 */
@Component("project-remove")
public final class ProjectRemoveCommand implements AsyncAbstractCommand {

    @Override
    public String description() {
        return "Remove selected project.";
    }

    @Override
    public void execute() {

    }

}
