package hmim.eteam.rest.backend.entity.learning;

import hmim.eteam.rest.backend.entity.core.CourseTheme;
import hmim.eteam.rest.backend.entity.core.CourseThemeEntry;

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
}
