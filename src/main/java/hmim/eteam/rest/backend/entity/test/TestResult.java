package hmim.eteam.rest.backend.entity.test;

import hmim.eteam.rest.backend.entity.core.SiteUser;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class TestResult {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @NotNull
    private Test test;

    @ManyToOne
    @NotNull
    private SiteUser siteUser;

    @NotNull
    private Integer totalPoints;

    private Date finishDate;

    public TestResult() {
    }

    public TestResult(Test test, SiteUser siteUser, int totalPoints, Date finishDate) {
        this.test = test;
        this.siteUser = siteUser;
        this.totalPoints = totalPoints;
        this.finishDate = finishDate;
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

    public int getTotalPoints() {
        return totalPoints;
    }

    public Date getFinishDate() {
        return finishDate;
    }
}
