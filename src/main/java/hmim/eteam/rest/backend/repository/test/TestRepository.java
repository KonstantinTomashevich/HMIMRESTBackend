package hmim.eteam.rest.backend.repository.test;

import hmim.eteam.rest.backend.entity.course.CourseTheme;
import hmim.eteam.rest.backend.entity.test.Test;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(exported = false)
public interface TestRepository extends CrudRepository<Test, Long> {
    List<Test> findByThemeOrderByPriorityAsc(CourseTheme theme);
}
