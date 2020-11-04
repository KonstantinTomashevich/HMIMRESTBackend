package hmim.eteam.rest.backend.repository.core;

import hmim.eteam.rest.backend.entity.core.Course;
import hmim.eteam.rest.backend.entity.core.CourseRole;
import hmim.eteam.rest.backend.entity.core.SiteUser;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CourseRoleRepository extends CrudRepository<CourseRole, Long> {
    List<CourseRole> findBySiteUserAndCourse(SiteUser siteUser, Course course);
}
