package ru.volnenko.se.repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import ru.volnenko.se.entity.Project;

/**
 * @author Denis Volnenko
 */
@Repository
public final class ProjectRepository implements ru.volnenko.se.api.repository.IProjectRepository {

    private final Map<String, Project> projects = new LinkedHashMap<>();

    @Override
    public Project createProject(final String name) {
        final Project project = new Project();
        project.setName(name);
        merge(project);
        return project;
    }

    @Override
    public Project merge(final Project project) {
        if (project == null) return null;
        projects.put(project.getId(), project);
        return project;
    }

    @Override
    public void merge(final Collection<Project> projects) {
        if (projects == null) return;
        for (final Project project: projects) merge(project);
    }

    @Override
    public void merge(final Project... projects) {
        if (projects == null) return;
        for (final Project project: projects) merge(project);
    }

    @Override
    public void load(final Collection<Project> projects) {
        clear();
        merge(projects);
    }

    @Override
    public void load(final Project... projects) {
        clear();
        merge(projects);
    }

    @Override
    public Project getProjectById(final String id) {
        if (id == null || id.isEmpty()) return null;
        return projects.get(id);
    }

    @Override
    public Project removeByOrderIndex(Integer orderIndex) {
        return null;
    }

    @Override
    public void removeProjectById(final String id) {
        if (id == null || id.isEmpty()) return;
        projects.remove(id);
    }

    @Override
    public List<Project> getListProject() {
        return new ArrayList<>(projects.values());
    }

    @Override
    public void clear() {
        projects.clear();
    }

}
