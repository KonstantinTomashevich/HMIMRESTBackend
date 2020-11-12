package hmim.eteam.rest.backend.entity.test;

import hmim.eteam.rest.backend.entity.IdentifiedEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class TestAnswer extends IdentifiedEntity {
    @ManyToOne
    @NotNull
    private TestQuestion question;

    @NotNull
    private String answer;

    @NotNull
    private Integer points;

    public TestAnswer() {
    }

    public TestAnswer(TestQuestion question, String answer, int points) {
        this.question = question;
        this.answer = answer;
        this.points = points;
    }

    public TestQuestion getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public int getPoints() {
        return points;
    }
}
