package hmim.eteam.rest.backend.repository.forum;

import hmim.eteam.rest.backend.entity.course.Course;
import hmim.eteam.rest.backend.entity.forum.ForumTheme;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(exported = false)
public interface ThemeRepository extends CrudRepository<ForumTheme, Long> {
    List<ForumTheme> findByCourseOrderByLastUpdateDateDesc(Course course);
}
