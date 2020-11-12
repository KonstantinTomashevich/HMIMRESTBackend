package hmim.eteam.rest.backend.entity.test;

import hmim.eteam.rest.backend.entity.core.CourseTheme;
import hmim.eteam.rest.backend.entity.util.IdentifiedEntity;
import hmim.eteam.rest.backend.entity.util.OrderedEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class Test extends OrderedEntity {
    @ManyToOne
    @NotNull
    private CourseTheme theme;

    @NotNull
    private String name;

    public Test() {
    }

    public Test(long priority, CourseTheme theme, String name) {
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
