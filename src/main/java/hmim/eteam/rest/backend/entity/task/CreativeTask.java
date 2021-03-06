package hmim.eteam.rest.backend.entity.task;

import hmim.eteam.rest.backend.entity.course.CourseTheme;
import hmim.eteam.rest.backend.entity.course.CourseThemeEntry;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
public class CreativeTask extends CourseThemeEntry {
    @NotNull
    private String text;

    public CreativeTask() {
    }

    public CreativeTask(long priority, CourseTheme theme, String name, String text) {
        super(priority, theme, name);
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public hmim.eteam.rest.backend.model.CreativeTask toApiRepresentation() {
        return new hmim.eteam.rest.backend.model.CreativeTask().
                id(getId()).
                name(getName()).
                priority(getPriority());
    }
}
