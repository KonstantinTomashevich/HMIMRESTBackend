package hmim.eteam.rest.backend.entity.core;

import javax.persistence.*;

@Entity
public class CourseRole {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private SiteUser siteUser;

    @ManyToOne
    private Course course;

    private Boolean isAdministrator;

    public CourseRole() {
    }

    public CourseRole(SiteUser siteUser, Course course, Boolean isAdministrator) {
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

    public Boolean isAdministrator() {
        return isAdministrator;
    }
}
