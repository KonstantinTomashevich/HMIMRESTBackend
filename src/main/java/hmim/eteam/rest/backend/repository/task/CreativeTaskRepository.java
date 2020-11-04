package hmim.eteam.rest.backend.repository.task;

import hmim.eteam.rest.backend.entity.core.CourseTheme;
import hmim.eteam.rest.backend.entity.task.CreativeTask;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CreativeTaskRepository extends CrudRepository<CreativeTask, Long> {
    List<CreativeTask> findByThemeOrderByIndexAsc(CourseTheme theme);
}
