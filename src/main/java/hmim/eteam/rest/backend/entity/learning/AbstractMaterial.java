package hmim.eteam.rest.backend.entity.learning;

import hmim.eteam.rest.backend.entity.core.CourseTheme;
import hmim.eteam.rest.backend.entity.util.OrderedEntity;

import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

@MappedSuperclass
public abstract class AbstractMaterial extends OrderedEntity {
    @NotNull
    private String name;

    @ManyToOne
    @NotNull
    private CourseTheme theme;

    public AbstractMaterial() {
    }

    public AbstractMaterial(long priority, String name, CourseTheme theme) {
        super(priority);
        this.name = name;
        this.theme = theme;
    }

    public String getName() {
        return name;
    }

    public CourseTheme getTheme() {
        return theme;
    }
}
