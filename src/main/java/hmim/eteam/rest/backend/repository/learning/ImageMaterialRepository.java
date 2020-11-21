package hmim.eteam.rest.backend.repository.learning;

import hmim.eteam.rest.backend.entity.course.CourseTheme;
import hmim.eteam.rest.backend.entity.learning.ImageMaterial;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(exported = false)
public interface ImageMaterialRepository extends CrudRepository<ImageMaterial, Long> {
    List<ImageMaterial> findByThemeOrderByPriorityAsc(CourseTheme theme);
}
