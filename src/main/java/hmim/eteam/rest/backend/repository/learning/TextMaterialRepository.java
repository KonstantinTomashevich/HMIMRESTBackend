package hmim.eteam.rest.backend.repository.learning;

import hmim.eteam.rest.backend.entity.course.CourseTheme;
import hmim.eteam.rest.backend.entity.learning.TextMaterial;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(exported = false)
public interface TextMaterialRepository extends CrudRepository<TextMaterial, Long> {
    List<TextMaterial> findByThemeOrderByPriorityAsc(CourseTheme theme);
}
