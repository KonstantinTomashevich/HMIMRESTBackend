package hmim.eteam.rest.backend.representation.output;

import hmim.eteam.rest.backend.entity.course.CourseThemeEntry;

public class CourseThemeEntryBriefInfo {
    public String type;
    public Long id;
    public String name;

    public CourseThemeEntryBriefInfo(CourseThemeEntry entry) {
        type = entry.getClass().getSimpleName();
        id = entry.getId();
        name = entry.getName();
    }
}
