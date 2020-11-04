package hmim.eteam.rest.backend.entity.learning;

import hmim.eteam.rest.backend.entity.core.CourseTheme;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class VideoMaterial {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @NotNull
    private CourseTheme theme;

    @NotNull
    private String name;

    @NotNull
    private String url;

    @NotNull
    private Long index;

    public VideoMaterial() {
    }

    public VideoMaterial(CourseTheme theme, String name, String url, long index) {
        this.theme = theme;
        this.name = name;
        this.url = url;
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

    public String getUrl() {
        return url;
    }

    public long getIndex() {
        return index;
    }
}
