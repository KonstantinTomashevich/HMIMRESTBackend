package hmim.eteam.rest.backend.entity.task;

import hmim.eteam.rest.backend.entity.core.SiteUser;
import hmim.eteam.rest.backend.entity.util.IdentifiedEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class CreativeTaskAnswer extends IdentifiedEntity {
    @ManyToOne
    @NotNull
    private CreativeTask task;

    @ManyToOne
    @NotNull
    private SiteUser student;

    @NotNull
    private String answer;

    @NotNull
    private Date date;

    public CreativeTaskAnswer() {
    }

    public CreativeTaskAnswer(CreativeTask task, SiteUser student, String answer, Date date) {
        this.task = task;
        this.student = student;
        this.answer = answer;
        this.date = date;
    }

    public CreativeTask getTask() {
        return task;
    }

    public SiteUser getStudent() {
        return student;
    }

    public String getAnswer() {
        return answer;
    }

    public Date getDate() {
        return date;
    }
}
