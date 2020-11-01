package hmim.eteam.rest.backend.entity.test;

import hmim.eteam.rest.backend.entity.core.SiteUser;

import javax.persistence.*;

@Entity
public class TestResult {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Test test;

    @ManyToOne
    private SiteUser siteUser;

    private Integer totalPoints;

    private Boolean finished;

    public TestResult() {
    }

    public TestResult(Test test, SiteUser siteUser, Integer totalPoints, Boolean finished) {
        this.test = test;
        this.siteUser = siteUser;
        this.totalPoints = totalPoints;
        this.finished = finished;
    }

    public Long getId() {
        return id;
    }

    public Test getTest() {
        return test;
    }

    public SiteUser getUser() {
        return siteUser;
    }

    public Integer getTotalPoints() {
        return totalPoints;
    }

    public Boolean getFinished() {
        return finished;
    }
}
