package hmim.eteam.rest.backend.entity.learning;

import hmim.eteam.rest.backend.entity.core.CourseTheme;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
public class ImageMaterial extends AbstractMaterial {
    @NotNull
    private String url;

    public ImageMaterial() {
    }

    public ImageMaterial(long priority, String name, CourseTheme theme, String url) {
        super(priority, name, theme);
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
