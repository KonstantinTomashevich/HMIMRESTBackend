package hmim.eteam.rest.backend.entity.test;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class TestQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @NotNull
    private Test test;

    @NotNull
    private String text;

    public TestQuestion() {
    }

    public Long getId() {
        return id;
    }

    public Test getTest() {
        return test;
    }

    public String getText() {
        return text;
    }
}
