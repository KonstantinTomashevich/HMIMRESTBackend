package hmim.eteam.rest.backend.entity.forum;

import hmim.eteam.rest.backend.entity.core.Course;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
public class ForumTheme {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @NotNull
    private Course course;

    @NotNull
    private String name;

    public ForumTheme() {
    }

    public ForumTheme(Course course, String name) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ForumTheme that = (ForumTheme) o;
        return id.equals(that.id) &&
                course.equals(that.course) &&
                name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
