package hmim.eteam.rest.backend.entity.task;

import hmim.eteam.rest.backend.entity.core.CourseTheme;

import javax.persistence.*;

@Entity
public class CreativeTask {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "theme_id")
    private CourseTheme theme;

    private String name;

    private String text;

    public CreativeTask() {
    }

    public CreativeTask(CourseTheme theme, String name, String text) {
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
