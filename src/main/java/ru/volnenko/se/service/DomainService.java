package ru.volnenko.se.service;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.volnenko.se.api.service.IDomainService;
import ru.volnenko.se.api.service.IProjectService;
import ru.volnenko.se.api.service.ITaskService;
import ru.volnenko.se.entity.Domain;

/**
 * @author Denis Volnenko
 * @author Shmelev Dmitry
 */
@Service
@Setter(onMethod_=@Autowired)
public final class DomainService implements IDomainService {

    private IProjectService projectService;
    private ITaskService taskService;

    @Override
    public void load(final Domain domain) {
        if (domain == null) return;
        projectService.saveAll(domain.getProjects());
        taskService.load(domain.getTasks());
    }

    @Override
    public void export(final Domain domain) {
        if (domain == null) return;
        domain.setProjects(projectService.getListProject());
        domain.setTasks(taskService.getListTask());
    }

}
