package hmim.eteam.rest.backend.entity.test;

import hmim.eteam.rest.backend.entity.util.IdentifiedEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class TestUserAnswer extends IdentifiedEntity {
    @ManyToOne
    @NotNull
    private TestAnswer answer;

    @ManyToOne
    @NotNull
    private TestResult result;

    public TestUserAnswer() {
    }

    public TestUserAnswer(TestAnswer answer, TestResult result) {
        this.answer = answer;
        this.result = result;
    }

    public TestAnswer getAnswer() {
        return answer;
    }

    public TestResult getResult() {
        return result;
    }

    public hmim.eteam.rest.backend.model.TestAnswer toApiRepresentation() {
        return new hmim.eteam.rest.backend.model.TestAnswer().
                id(getId()).
                name(answer.getAnswer()).
                priority(Math.toIntExact(answer.getPriority())).
                score(answer.getPoints()).
                participant(result.getUser().getId());
    }
}
