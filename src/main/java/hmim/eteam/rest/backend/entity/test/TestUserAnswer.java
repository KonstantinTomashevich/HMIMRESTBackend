package hmim.eteam.rest.backend.entity.test;

import hmim.eteam.rest.backend.entity.core.SiteUser;
import hmim.eteam.rest.backend.entity.util.IdentifiedEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class TestUserAnswer extends IdentifiedEntity {
    @ManyToOne
    @NotNull
    private TestAnswer answer;

    @ManyToOne
    @NotNull
    private SiteUser siteUser;

    @ManyToOne
    @NotNull
    private TestResult result;

    public TestUserAnswer() {
    }

    public TestUserAnswer(TestAnswer answer, SiteUser siteUser, TestResult result) {
        this.answer = answer;
        this.siteUser = siteUser;
        this.result = result;
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
