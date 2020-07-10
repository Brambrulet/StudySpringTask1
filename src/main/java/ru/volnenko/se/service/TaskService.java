package ru.volnenko.se.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.volnenko.se.api.service.ITaskService;
import ru.volnenko.se.entity.Project;
import ru.volnenko.se.entity.Task;
import ru.volnenko.se.repository.ProjectRepository;
import ru.volnenko.se.repository.TaskRepository;

/**
 * @author Denis Volnenko
 * @author Shmelev Dmitry
 */
@Service
@Setter(onMethod_=@Autowired)
public class TaskService implements ITaskService {

    private TaskRepository taskRepository;
    private ProjectRepository projectRepository;

    @Override
    @Transactional
    public Task createTask(final String name) {
        if (name == null || name.isEmpty()) return null;
        return taskRepository.save(new Task().setName(name));
    }

    @Override
    @Transactional
    public Task getTaskById(final String id) {
        return taskRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void removeTaskById(final String id) {
        taskRepository.deleteById(id);
    }

    @Override
    @Transactional
    public List<Task> getListTask() {
        List<Task> tasks = new ArrayList<>();
        taskRepository.findAll().forEach(tasks::add);

        return tasks;
    }

    @Override
    @Transactional
    public void clear() {
        taskRepository.deleteAll();
    }

    @Override
    @Transactional
    public Task createTaskByProject(final String projectId, final String taskName) {
        final Project project = projectRepository.findById(projectId).orElse(null);
        if (project == null) return null;
        return taskRepository.save(new Task().setProjectId(taskName));
    }

    @Override
    @Transactional
    public void load(Task... tasks) {
        taskRepository.saveAll(Arrays.asList(tasks));
    }

    @Override
    @Transactional
    public void load(Collection<Task> tasks) {
        taskRepository.saveAll(tasks);
    }
}
