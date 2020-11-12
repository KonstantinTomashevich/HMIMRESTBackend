package hmim.eteam.rest.backend.repository.core;

import hmim.eteam.rest.backend.entity.core.CourseTheme;
import hmim.eteam.rest.backend.entity.core.CourseThemeEntry;
import hmim.eteam.rest.backend.entity.learning.VideoMaterial;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CourseThemeEntryRepository<EntryType extends CourseThemeEntry>
        extends CrudRepository<EntryType, Long> {
    List<VideoMaterial> findByThemeOrderByPriorityAsc(CourseTheme theme);
}
