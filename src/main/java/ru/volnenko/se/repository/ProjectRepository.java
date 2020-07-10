package ru.volnenko.se.repository;

import java.util.Date;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.volnenko.se.entity.Project;

/**
 * @author Denis Volnenko
 * @author Dmitry Shmelev
 */
@Repository
public interface ProjectRepository extends CrudRepository<Project, String> {
    void deleteAllByDateBegin(@Param("dateBegin") Date dateBegin);

    @Query(value = "delete from Project project "
            + "         where project in (select domain.projects "
            + "                                 from Task task "
            + "                                 inner join Domain domain "
            + "                                 where task MEMBER of domain.tasks)")
    void deleteAllByTaskName(@Param("taskName") String taskName);
}
