package hmim.eteam.rest.backend.entity.course;

import hmim.eteam.rest.backend.entity.util.OrderedEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class CourseTheme extends OrderedEntity {
    @ManyToOne
    @NotNull
    private Course course;

    @NotNull
    private String name;

    public CourseTheme() {
    }

    public CourseTheme(long priority, Course course, String name) {
        super(priority);
        this.course = course;
        this.name = name;
    }

    public Course getCourse() {
        return course;
    }

    public String getName() {
        return name;
    }
}
