package hmim.eteam.rest.backend.entity.task;

import hmim.eteam.rest.backend.entity.core.CourseTheme;
import hmim.eteam.rest.backend.entity.core.CourseThemeEntry;

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
}
