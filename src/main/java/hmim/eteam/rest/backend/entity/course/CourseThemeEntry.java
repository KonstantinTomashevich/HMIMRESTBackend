package hmim.eteam.rest.backend.entity.course;

import hmim.eteam.rest.backend.entity.util.OrderedEntity;

import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

@MappedSuperclass
public class CourseThemeEntry extends OrderedEntity {
    @ManyToOne
    @NotNull
    private CourseTheme theme;

    @NotNull
    private String name;

    public CourseThemeEntry() {
    }

    public CourseThemeEntry(long priority, CourseTheme theme, String name) {
        super(priority);
        this.theme = theme;
        this.name = name;
    }

    public CourseTheme getTheme() {
        return theme;
    }

    public String getName() {
        return name;
    }
}
