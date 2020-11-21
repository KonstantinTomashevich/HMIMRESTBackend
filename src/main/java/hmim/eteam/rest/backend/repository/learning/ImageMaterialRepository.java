package hmim.eteam.rest.backend.repository.learning;

import hmim.eteam.rest.backend.entity.course.CourseTheme;
import hmim.eteam.rest.backend.entity.learning.ImageMaterial;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ImageMaterialRepository extends CrudRepository<ImageMaterial, Long> {
    List<ImageMaterial> findByThemeOrderByPriorityAsc(CourseTheme theme);
}
