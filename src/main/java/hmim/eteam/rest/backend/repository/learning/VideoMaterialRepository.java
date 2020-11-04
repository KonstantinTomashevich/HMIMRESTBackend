package hmim.eteam.rest.backend.repository.learning;

import hmim.eteam.rest.backend.entity.core.CourseTheme;
import hmim.eteam.rest.backend.entity.learning.VideoMaterial;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface VideoMaterialRepository extends CrudRepository<VideoMaterial, Long> {
    List<VideoMaterial> findByThemeOrderByIndexAsc(CourseTheme theme);
}
