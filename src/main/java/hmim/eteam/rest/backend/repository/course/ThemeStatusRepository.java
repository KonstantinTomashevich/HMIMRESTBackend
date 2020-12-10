package hmim.eteam.rest.backend.repository.course;

import hmim.eteam.rest.backend.entity.course.CourseTheme;
import hmim.eteam.rest.backend.entity.course.ThemeStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(exported = false)
public interface ThemeStatusRepository extends CrudRepository<ThemeStatus, Long> {
    List<ThemeStatus> findByTheme(CourseTheme theme);
}
