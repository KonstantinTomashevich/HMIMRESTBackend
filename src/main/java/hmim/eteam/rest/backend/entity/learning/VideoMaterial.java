package hmim.eteam.rest.backend.entity.learning;

import hmim.eteam.rest.backend.entity.core.CourseTheme;

import javax.persistence.*;

@Entity
public class VideoMaterial {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "theme_id")
    private CourseTheme theme;

    private String name;

    private String url;

    public VideoMaterial() {
    }

    public VideoMaterial(CourseTheme theme, String name, String url) {
        this.theme = theme;
        this.name = name;
        this.url = url;
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

    public String getUrl() {
        return url;
    }
}