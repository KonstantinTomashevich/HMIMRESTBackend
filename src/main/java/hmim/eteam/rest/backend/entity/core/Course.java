package hmim.eteam.rest.backend.entity.core;

import hmim.eteam.rest.backend.entity.util.IdentifiedEntity;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
public class Course extends IdentifiedEntity {
    @NotNull
    private String name;

    public Course() {

    }

    public Course(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
