package hmim.eteam.rest.backend.entity.core;

import hmim.eteam.rest.backend.entity.util.IdentifiedEntity;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class CourseRole extends IdentifiedEntity {
    @ManyToOne
    @NotNull
    private SiteUser siteUser;

    @ManyToOne
    @NotNull
    private Course course;

    @NotNull
    @Enumerated
    private UserRole role;

    public CourseRole() {
    }

    public CourseRole(SiteUser siteUser, Course course, UserRole role) {
        this.siteUser = siteUser;
        this.course = course;
        this.role = role;
    }

    public SiteUser getUser() {
        return siteUser;
    }

    public Course getCourse() {
        return course;
    }

    public UserRole getRole() {
        return role;
    }
}
