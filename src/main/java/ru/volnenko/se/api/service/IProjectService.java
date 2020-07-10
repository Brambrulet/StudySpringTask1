package ru.volnenko.se.api.service;

import java.util.Optional;
import ru.volnenko.se.entity.Project;

import java.util.Collection;
import java.util.List;

/**
 * @author Denis Volnenko
 */
public interface IProjectService {

    Project createProject(String name);

    Project saveAll(Project project);

    Optional<Project> findById(String id);

    void deleteById(String id);

    List<Project> getListProject();

    void deleteAll();

    void saveAll(Project... projects);

    void saveAll(Collection<Project> projects);

    void deleteAllByTaskName(String taskName);
}
