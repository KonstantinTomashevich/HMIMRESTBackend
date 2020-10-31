package hmim.eteam.rest.backend.entity.test;

import hmim.eteam.rest.backend.entity.core.User;

import javax.persistence.*;

@Entity
public class TestUserAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "answer_id")
    private TestAnswer answer;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "result_id")
    private TestResult result;

    public TestUserAnswer() {
    }

    public TestUserAnswer(TestAnswer answer, User user, TestResult result) {
        this.answer = answer;
        this.user = user;
        this.result = result;
    }

    public Long getId() {
        return id;
    }

    public TestAnswer getAnswer() {
        return answer;
    }

    public User getUser() {
        return user;
    }

    public TestResult getResult() {
        return result;
    }
}
