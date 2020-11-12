package hmim.eteam.rest.backend.entity.test;

import hmim.eteam.rest.backend.entity.core.SiteUser;
import hmim.eteam.rest.backend.entity.util.IdentifiedEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class TestResult extends IdentifiedEntity {
    @ManyToOne
    @NotNull
    private Test test;

    @ManyToOne
    @NotNull
    private SiteUser siteUser;

    @NotNull
    private Integer score;

    private Date finishDate;

    public TestResult() {
    }

    public TestResult(Test test, SiteUser siteUser, int score, Date finishDate) {
        this.test = test;
        this.siteUser = siteUser;
        this.score = score;
        this.finishDate = finishDate;
    }

    public Test getTest() {
        return test;
    }

    public SiteUser getUser() {
        return siteUser;
    }

    public int getScore() {
        return score;
    }

    public Date getFinishDate() {
        return finishDate;
    }
}
