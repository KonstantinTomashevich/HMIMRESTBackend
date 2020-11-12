package hmim.eteam.rest.backend.repository.user;

import hmim.eteam.rest.backend.entity.course.Course;
import hmim.eteam.rest.backend.entity.user.CourseRole;
import hmim.eteam.rest.backend.entity.user.SiteUser;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CourseRoleRepository extends CrudRepository<CourseRole, Long> {
    List<CourseRole> findBySiteUserAndCourse(SiteUser siteUser, Course course);
}
