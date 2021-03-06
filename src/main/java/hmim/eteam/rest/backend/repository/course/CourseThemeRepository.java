package hmim.eteam.rest.backend.repository.course;

import hmim.eteam.rest.backend.entity.course.Course;
import hmim.eteam.rest.backend.entity.course.CourseTheme;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(exported = false)
public interface CourseThemeRepository extends CrudRepository<CourseTheme, Long> {
    List<CourseTheme> findByCourseOrderByPriorityAsc(Course course);
}
