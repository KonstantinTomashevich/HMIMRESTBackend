package hmim.eteam.rest.backend.entity.core;

import javax.persistence.*;

@Entity
public class CourseTheme {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Course course;

    private String name;

    public CourseTheme() {
    }

    public CourseTheme(Course course, String name) {
        this.course = course;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public Course getCourse() {
        return course;
    }

    public String getName() {
        return name;
    }
}
