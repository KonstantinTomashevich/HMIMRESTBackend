package hmim.eteam.rest.backend.entity.test;

import hmim.eteam.rest.backend.entity.util.IdentifiedEntity;
import hmim.eteam.rest.backend.entity.util.OrderedEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class TestQuestion extends OrderedEntity {
    @ManyToOne
    @NotNull
    private Test test;

    @NotNull
    private String text;

    public TestQuestion() {
    }

    public TestQuestion(long priority, Test test, String text) {
        super(priority);
        this.test = test;
        this.text = text;
    }

    public Test getTest() {
        return test;
    }

    public String getText() {
        return text;
    }
}
