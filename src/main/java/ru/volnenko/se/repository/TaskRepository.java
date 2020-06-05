package ru.volnenko.se.repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import ru.volnenko.se.entity.Task;

/**
 * @author Denis Volnenko
 */
@Repository
public final class TaskRepository implements ru.volnenko.se.api.repository.ITaskRepository {

    private final Map<String, Task> tasks = new LinkedHashMap<>();

    @Override
    public Task createTask(final String name) {
        final Task task = new Task();
        task.setName(name);
        merge(task);
        return task;
    }

    @Override
    public Task getTaskById(final String id) {
        if (id == null || id.isEmpty()) return null;
        return tasks.get(id);
    }

    @Override
    public Task getByOrderIndex(final Integer orderIndex) {
        if (orderIndex == null) return null;
        return getListTask().get(orderIndex);
    }

    @Override
    public void merge(final Task... tasks) {
        for (final Task task: tasks) merge(task);
    }

    @Override
    public void merge(final Collection<Task> tasks) {
        for (final Task task: tasks) merge(task);
    }

    @Override
    public void load(final Collection<Task> tasks) {
        clear();
        merge(tasks);
    }

    @Override
    public void load(final Task... tasks) {
        clear();
        merge(tasks);
    }

    @Override
    public Task merge(final Task task) {
        if (task == null) return null;
        tasks.put(task.getId(), task);
        return task;
    }

    @Override
    public void removeTaskById(final String id) {
        if (id == null || id.isEmpty()) return;
        tasks.remove(id);
    }

    @Override
    public void removeTaskByOrderIndex(final Integer orderIndex) {
        Task task = getByOrderIndex(orderIndex);
        if (task == null) return;
        removeTaskById(task.getId());
    }

    @Override
    public List<Task> getListTask() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public void clear() {
        tasks.clear();
    }

}
