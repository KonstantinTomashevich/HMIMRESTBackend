package hmim.eteam.rest.backend.entity.learning;

import hmim.eteam.rest.backend.entity.core.CourseTheme;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class TextMaterial {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @NotNull
    private CourseTheme theme;

    @NotNull
    private String name;

    @NotNull
    private String text;

    @NotNull
    private Long index;

    public TextMaterial() {
    }

    public TextMaterial(CourseTheme theme, String name, String text, long index) {
        this.theme = theme;
        this.name = name;
        this.text = text;
        this.index = index;
    }

    public Long getId() {
        return id;
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

    public long getIndex() {
        return index;
    }
}
