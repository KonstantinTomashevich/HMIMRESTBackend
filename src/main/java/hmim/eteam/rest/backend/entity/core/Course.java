package hmim.eteam.rest.backend.entity.core;

import hmim.eteam.rest.backend.entity.IdentifiedEntity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
