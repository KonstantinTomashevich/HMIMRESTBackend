package hmim.eteam.rest.backend.entity.forum;

import hmim.eteam.rest.backend.entity.core.CourseTheme;

import javax.persistence.*;

@Entity
public class ForumTheme {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "theme_id")
    private CourseTheme theme;

    private String name;

    public ForumTheme() {
    }

    public ForumTheme(CourseTheme theme, String name) {
        this.theme = theme;
        this.name = name;
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
}
