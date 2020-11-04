package hmim.eteam.rest.backend.entity.core;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class CourseRole {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @NotNull
    private SiteUser siteUser;

    @ManyToOne
    @NotNull
    private Course course;

    @NotNull
    private Boolean isAdministrator;

    public CourseRole() {
    }

    public CourseRole(SiteUser siteUser, Course course, boolean isAdministrator) {
        this.siteUser = siteUser;
        this.course = course;
        this.isAdministrator = isAdministrator;
    }

    public Long getId() {
        return id;
    }

    public SiteUser getUser() {
        return siteUser;
    }

    public Course getCourse() {
        return course;
    }

    public boolean isAdministrator() {
        return isAdministrator;
    }
}
