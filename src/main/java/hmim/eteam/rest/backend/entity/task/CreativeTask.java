package hmim.eteam.rest.backend.entity.task;

import hmim.eteam.rest.backend.entity.core.CourseTheme;
import hmim.eteam.rest.backend.entity.util.IdentifiedEntity;
import hmim.eteam.rest.backend.entity.util.OrderedEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class CreativeTask extends OrderedEntity {
    @ManyToOne
    @NotNull
    private CourseTheme theme;

    @NotNull
    private String name;

    @NotNull
    private String text;

    public CreativeTask() {
    }

    public CreativeTask(long priority, CourseTheme theme, String name, String text) {
        this.theme = theme;
        this.name = name;
        this.text = text;
    }

    public CourseTheme getTheme() {
        return theme;
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }
}
