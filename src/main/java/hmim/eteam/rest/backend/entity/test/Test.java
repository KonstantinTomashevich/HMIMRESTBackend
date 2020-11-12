package hmim.eteam.rest.backend.entity.test;

import hmim.eteam.rest.backend.entity.core.CourseTheme;
import hmim.eteam.rest.backend.entity.core.CourseThemeEntry;

import javax.persistence.Entity;

@Entity
public class Test extends CourseThemeEntry {
    public Test() {
    }

    public Test(long priority, CourseTheme theme, String name) {
        super(priority, theme, name);
    }
}
