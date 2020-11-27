package hmim.eteam.rest.backend.entity.course;

import hmim.eteam.rest.backend.entity.forum.ForumTheme;
import hmim.eteam.rest.backend.entity.util.OrderedEntity;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Course extends OrderedEntity {
    @NotNull
    private String name;

    @OneToMany
    private List<CourseTheme> courseThemes = new ArrayList<>();

    @OneToMany
    private List<ForumTheme> forumThemes = new ArrayList<>();

    public Course() {

    }

    public Course(long priority, String name) {
        super(priority);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public hmim.eteam.rest.backend.model.Course toApiRepresentation() {
        hmim.eteam.rest.backend.model.Course course = new hmim.eteam.rest.backend.model.Course();
        course.id(getId().toString()).
                name(name).
                priority(getPriority().intValue());

        courseThemes.forEach(courseTheme -> course.addThemesItem(courseTheme.toApiRepresentation()));
        forumThemes.forEach(forumTheme -> course.addForumThemesItem(forumTheme.toApiRepresentation()));
        return course;
    }
}
