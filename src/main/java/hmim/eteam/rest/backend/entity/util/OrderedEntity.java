package hmim.eteam.rest.backend.entity.util;

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

@MappedSuperclass
public abstract class OrderedEntity extends IdentifiedEntity {
    @NotNull
    private Long priority;

    public OrderedEntity() {
    }

    public OrderedEntity(long priority) {
        this.priority = priority;
    }

    public Long getPriority() {
        return priority;
    }
}
