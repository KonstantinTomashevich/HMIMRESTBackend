package hmim.eteam.rest.backend.repository.test;

import hmim.eteam.rest.backend.entity.course.CourseTheme;
import hmim.eteam.rest.backend.entity.test.Test;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TestRepository extends CrudRepository<Test, Long> {
    List<Test> findByThemeOrderByPriorityAsc(CourseTheme theme);
}
