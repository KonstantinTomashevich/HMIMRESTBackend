package hmim.eteam.rest.backend.entity.learning;

import hmim.eteam.rest.backend.entity.course.CourseTheme;
import hmim.eteam.rest.backend.entity.course.CourseThemeEntry;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
public class VideoMaterial extends CourseThemeEntry {
    @NotNull
    private String url;

    public VideoMaterial() {
    }

    public VideoMaterial(long priority, CourseTheme theme, String name, String url) {
        super(priority, theme, name);
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public hmim.eteam.rest.backend.model.VideoMaterial toApiRepresentation() {
        return new hmim.eteam.rest.backend.model.VideoMaterial().
                id(getId()).
                name(getName()).
                priority(getPriority()).
                url(getUrl());
    }
}
