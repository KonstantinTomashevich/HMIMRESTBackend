package hmim.eteam.rest.backend.repository.course;

import hmim.eteam.rest.backend.entity.course.Course;
import org.springframework.data.repository.CrudRepository;

public interface CourseRepository extends CrudRepository<Course, Long> {
}
