package hmim.eteam.rest.backend.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.Objects;

@MappedSuperclass
public abstract class IdentifiedEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id = null;

    public Long getId() {
        return id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
