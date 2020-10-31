package hmim.eteam.rest.backend.entity.learning;

import hmim.eteam.rest.backend.entity.core.CourseTheme;

import javax.persistence.*;

@Entity
public class TextMaterial {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "theme_id")
    private CourseTheme theme;

    private String name;

    private String text;

    public TextMaterial() {
    }

    public TextMaterial(CourseTheme theme, String name, String text) {
        this.theme = theme;
        this.name = name;
        this.text = text;
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
}
