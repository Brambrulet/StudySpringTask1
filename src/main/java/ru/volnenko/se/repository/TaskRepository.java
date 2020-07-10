package ru.volnenko.se.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.volnenko.se.entity.Task;

/**
 * @author Denis Volnenko
 */
@Repository
public interface TaskRepository extends CrudRepository<Task, String> {
}
