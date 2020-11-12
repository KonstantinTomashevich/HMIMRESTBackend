package hmim.eteam.rest.backend.entity.test;

import hmim.eteam.rest.backend.entity.util.OrderedEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
public class TestAnswer extends OrderedEntity {
    @ManyToOne
    @NotNull
    private TestQuestion question;

    @NotNull
    private String answer;

    @NotNull
    @Min(0)
    @Max(255)
    private Integer points;

    public TestAnswer() {
    }

    public TestAnswer(long priority, TestQuestion question, String answer, int points) {
        super(priority);
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
