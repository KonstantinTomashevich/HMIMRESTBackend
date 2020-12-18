package hmim.eteam.rest.backend.repository.user;

import hmim.eteam.rest.backend.entity.course.Course;
import hmim.eteam.rest.backend.entity.user.CourseRole;
import hmim.eteam.rest.backend.entity.user.SiteUser;
import hmim.eteam.rest.backend.entity.user.UserRole;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(exported = false)
public interface CourseRoleRepository extends CrudRepository<CourseRole, Long> {
    Optional<CourseRole> findTopBySiteUserAndCourse(SiteUser siteUser, Course course);

    List<CourseRole> findByCourse(Course course);

    Long countDistinctByCourseAndRole(Course course, UserRole role);
}
