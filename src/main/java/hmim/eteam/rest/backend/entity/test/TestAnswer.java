package hmim.eteam.rest.backend.entity.test;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class TestAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @NotNull
    private TestQuestion question;

    @NotNull
    private String answer;

    @NotNull
    private Integer points;

    public TestAnswer() {
    }

    public TestAnswer(TestQuestion question, String answer, Integer points) {
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

    public Integer getPoints() {
        return points;
    }
}
