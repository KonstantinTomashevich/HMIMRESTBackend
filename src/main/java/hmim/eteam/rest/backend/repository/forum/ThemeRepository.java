package hmim.eteam.rest.backend.repository.forum;

import hmim.eteam.rest.backend.entity.course.Course;
import hmim.eteam.rest.backend.entity.forum.ForumTheme;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ThemeRepository extends CrudRepository<ForumTheme, Long> {
    List<ForumTheme> findByCourse(Course course);
}
