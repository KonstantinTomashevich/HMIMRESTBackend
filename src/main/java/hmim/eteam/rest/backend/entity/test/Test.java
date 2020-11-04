package hmim.eteam.rest.backend.entity.test;

import hmim.eteam.rest.backend.entity.core.CourseTheme;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @NotNull
    private CourseTheme theme;

    @NotNull
    private String name;

    @NotNull
    private Long index;

    public Test() {
    }

    public Test(CourseTheme theme, String name, long index) {
        this.theme = theme;
        this.name = name;
        this.index = index;
    }

    public Long getId() {
        return id;
    }

    public CourseTheme getTheme() {
        return theme;
    }

    public String getName() {
        return name;
    }

    public Long getIndex() {
        return index;
    }
}
