package hmim.eteam.rest.backend.entity.test;

import hmim.eteam.rest.backend.entity.core.SiteUser;

import javax.persistence.*;

@Entity
public class TestUserAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private TestAnswer answer;

    @ManyToOne
    private SiteUser siteUser;

    @ManyToOne
    private TestResult result;

    public TestUserAnswer() {
    }

    public TestUserAnswer(TestAnswer answer, SiteUser siteUser, TestResult result) {
        this.answer = answer;
        this.siteUser = siteUser;
        this.result = result;
    }

    public Long getId() {
        return id;
    }

    public TestAnswer getAnswer() {
        return answer;
    }

    public SiteUser getUser() {
        return siteUser;
    }

    public TestResult getResult() {
        return result;
    }
}
