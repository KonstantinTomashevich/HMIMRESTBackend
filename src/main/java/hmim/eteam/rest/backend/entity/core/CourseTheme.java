package hmim.eteam.rest.backend.entity.core;

import hmim.eteam.rest.backend.entity.IdentifiedEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class CourseTheme extends IdentifiedEntity {
    @ManyToOne
    @NotNull
    private Course course;

    @NotNull
    private String name;

    @NotNull
    private Long index;

    public CourseTheme() {
    }

    public CourseTheme(Course course, String name, long index) {
        this.course = course;
        this.name = name;
        this.index = index;
    }

    public Course getCourse() {
        return course;
    }

    public String getName() {
        return name;
    }

    public long getIndex() {
        return index;
    }
}
