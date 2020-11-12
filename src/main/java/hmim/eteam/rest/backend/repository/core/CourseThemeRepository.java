package hmim.eteam.rest.backend.repository.core;

import hmim.eteam.rest.backend.entity.core.Course;
import hmim.eteam.rest.backend.entity.core.CourseTheme;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CourseThemeRepository extends CrudRepository<CourseTheme, Long> {
    List<CourseTheme> findByCourseOrderByPriorityAsc(Course course);
}
