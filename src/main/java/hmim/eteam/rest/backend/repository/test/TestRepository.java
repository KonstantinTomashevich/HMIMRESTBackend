package hmim.eteam.rest.backend.repository.test;

import hmim.eteam.rest.backend.entity.core.CourseTheme;
import hmim.eteam.rest.backend.entity.test.Test;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TestRepository extends CrudRepository<Test, Long> {
    List<Test> findByThemeOrderByIndexAsc(CourseTheme theme);
}
