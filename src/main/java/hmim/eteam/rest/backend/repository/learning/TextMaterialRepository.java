package hmim.eteam.rest.backend.repository.learning;

import hmim.eteam.rest.backend.entity.core.CourseTheme;
import hmim.eteam.rest.backend.entity.learning.TextMaterial;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TextMaterialRepository extends CrudRepository<TextMaterial, Long> {
    List<TextMaterial> findByThemeOrderByPriorityAsc(CourseTheme theme);
}
