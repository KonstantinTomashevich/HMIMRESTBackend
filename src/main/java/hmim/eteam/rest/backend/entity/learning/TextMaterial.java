package hmim.eteam.rest.backend.entity.learning;

import hmim.eteam.rest.backend.entity.course.CourseTheme;
import hmim.eteam.rest.backend.entity.course.CourseThemeEntry;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
public class TextMaterial extends CourseThemeEntry {
    @NotNull
    private String text;

    public TextMaterial() {
    }

    public TextMaterial(long priority, CourseTheme theme, String name, String text) {
        super(priority, theme, name);
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public hmim.eteam.rest.backend.model.TextMaterial toApiRepresentation() {
        return new hmim.eteam.rest.backend.model.TextMaterial().
                id(getId()).
                name(getName()).
                priority(getPriority()).
                text(text);
    }
}
