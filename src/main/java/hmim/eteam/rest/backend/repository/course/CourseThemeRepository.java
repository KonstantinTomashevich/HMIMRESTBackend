package hmim.eteam.rest.backend.repository.course;

import hmim.eteam.rest.backend.entity.course.Course;
import hmim.eteam.rest.backend.entity.course.CourseTheme;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CourseThemeRepository extends CrudRepository<CourseTheme, Long> {
    List<CourseTheme> findByCourseOrderByPriorityAsc(Course course);
}
