package hmim.eteam.rest.backend.repository.course;

import hmim.eteam.rest.backend.entity.course.Course;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface CourseRepository extends CrudRepository<Course, Long> {
}
