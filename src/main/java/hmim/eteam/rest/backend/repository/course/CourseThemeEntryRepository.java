package hmim.eteam.rest.backend.repository.course;

import hmim.eteam.rest.backend.entity.course.CourseTheme;
import hmim.eteam.rest.backend.entity.course.CourseThemeEntry;
import hmim.eteam.rest.backend.entity.learning.VideoMaterial;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CourseThemeEntryRepository<EntryType extends CourseThemeEntry>
        extends CrudRepository<EntryType, Long> {
    List<VideoMaterial> findByThemeOrderByPriorityAsc(CourseTheme theme);
}
