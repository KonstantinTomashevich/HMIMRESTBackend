package hmim.eteam.rest.backend.entity.learning;

import hmim.eteam.rest.backend.entity.core.CourseTheme;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
public class TextMaterial extends AbstractMaterial {
    @NotNull
    private String text;

    public TextMaterial() {
    }

    public TextMaterial(long priority,  String name, CourseTheme theme, String text) {
        super(priority, name, theme);
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
