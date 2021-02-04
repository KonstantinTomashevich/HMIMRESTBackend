package hmim.eteam.rest.backend.entity.test;

import hmim.eteam.rest.backend.entity.course.CourseTheme;
import hmim.eteam.rest.backend.entity.course.CourseThemeEntry;

import javax.persistence.Entity;

@Entity
public class Test extends CourseThemeEntry {
    public Test() {
    }

    public Test(long priority, CourseTheme theme, String name) {
        super(priority, theme, name);
    }

    public hmim.eteam.rest.backend.model.Test toApiRepresentation() {
        return new hmim.eteam.rest.backend.model.Test().
                id(getId()).
                name(getName()).
                priority(getPriority());
    }
}
