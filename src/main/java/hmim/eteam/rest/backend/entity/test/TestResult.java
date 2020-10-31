package hmim.eteam.rest.backend.entity.test;

import hmim.eteam.rest.backend.entity.core.User;

import javax.persistence.*;

@Entity
public class TestResult {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "test_id")
    private Test test;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Integer totalPoints;

    private Boolean finished;

    public TestResult() {
    }

    public TestResult(Test test, User user, Integer totalPoints, Boolean finished) {
        this.test = test;
        this.user = user;
        this.totalPoints = totalPoints;
        this.finished = finished;
    }

    public Long getId() {
        return id;
    }

    public Test getTest() {
        return test;
    }

    public User getUser() {
        return user;
    }

    public Integer getTotalPoints() {
        return totalPoints;
    }

    public Boolean getFinished() {
        return finished;
    }
}
