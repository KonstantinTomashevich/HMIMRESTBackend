package hmim.eteam.rest.backend.entity.core;

import javax.persistence.*;

@Entity
public class CourseRole {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    private Boolean isAdministrator;

    public CourseRole() {
    }

    public CourseRole(User user, Course course, Boolean isAdministrator) {
        this.user = user;
        this.course = course;
        this.isAdministrator = isAdministrator;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Course getCourse() {
        return course;
    }

    public Boolean isAdministrator() {
        return isAdministrator;
    }
}