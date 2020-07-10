package ru.volnenko.se.service;

import com.sun.deploy.util.ArrayUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import ru.volnenko.se.entity.Project;

import java.util.Collection;
import java.util.List;
import ru.volnenko.se.repository.ProjectRepository;

/**
 * @author Denis Volnenko
 * @author Shmelev Dmitry
 */
@Service
@Setter(onMethod_=@Autowired)
public class ProjectService implements ru.volnenko.se.api.service.IProjectService {

    private ProjectRepository projectRepository;

    @Override
    @Transactional
    public Project createProject(final String name) {
        if (name == null || name.isEmpty()) return null;
        return projectRepository.save(new Project().setName(name));
    }

    @Override
    @Transactional
    public Project saveAll(final Project project) {
        return projectRepository.save(project);
    }

    @Override
    @Transactional
    public Optional<Project> findById(final String id) {
        return projectRepository.findById(id);
    }

    @Override
    @Transactional
    public void deleteById(final String id) {
        projectRepository.deleteById(id);
    }

    @Override
    @Transactional
    public List<Project> getListProject() {
        List<Project> projects = new ArrayList<>();
        projectRepository.findAll().forEach(projects::add);

        return projects;
    }

    @Override
    @Transactional
    public void deleteAll() {
        projectRepository.deleteAll();
    }

    @Override
    @Transactional
    public void saveAll(Project... projects) {
        projectRepository.saveAll(Arrays.asList(projects));
    }

    @Override
    @Transactional
    public void saveAll(Collection<Project> projects) {
        projectRepository.saveAll(projects);
    }

    @Override
    @Transactional
    public void deleteAllByTaskName(String taskName) {
        assert StringUtils.isEmpty(taskName);

        projectRepository.deleteAllByTaskName(taskName);
    }

}
