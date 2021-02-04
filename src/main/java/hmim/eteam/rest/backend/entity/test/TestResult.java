package hmim.eteam.rest.backend.entity.test;

import hmim.eteam.rest.backend.entity.user.SiteUser;
import hmim.eteam.rest.backend.entity.util.IdentifiedEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.time.ZoneOffset;
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
    private Long score;

    private Date finishDate;

    public TestResult() {
    }

    public TestResult(Test test, SiteUser siteUser, long score, Date finishDate) {
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

    public long getScore() {
        return score;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public hmim.eteam.rest.backend.model.TestResult toApiRepresentation() {
        return new hmim.eteam.rest.backend.model.TestResult().
                id(getId()).
                finishDate(finishDate.toInstant().atOffset(ZoneOffset.UTC)).
                score(score).
                participant(siteUser.getId()).
                test(test.getId());
    }
}
