package hmim.eteam.rest.backend.entity.course;

import hmim.eteam.rest.backend.entity.user.SiteUser;
import hmim.eteam.rest.backend.entity.util.IdentifiedEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class ThemeStatus extends IdentifiedEntity {
    @NotNull
    private Boolean seen;

    @ManyToOne
    @NotNull
    private CourseTheme theme;

    @ManyToOne
    @NotNull
    private SiteUser siteUser;

    public ThemeStatus() {
    }

    public ThemeStatus(boolean seen, CourseTheme theme, SiteUser siteUser) {
        this.seen = seen;
        this.theme = theme;
        this.siteUser = siteUser;
    }

    public Boolean getSeen() {
        return seen;
    }

    public CourseTheme getTheme() {
        return theme;
    }

    public SiteUser getSiteUser() {
        return siteUser;
    }
}
