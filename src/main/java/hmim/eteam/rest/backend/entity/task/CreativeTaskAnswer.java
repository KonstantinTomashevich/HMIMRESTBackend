package hmim.eteam.rest.backend.entity.task;

import hmim.eteam.rest.backend.entity.user.SiteUser;
import hmim.eteam.rest.backend.entity.util.IdentifiedEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @OneToMany
    private List<CreativeTaskGrade> grades = new ArrayList<>();

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

    public hmim.eteam.rest.backend.model.CreativeTaskAnswer toApiRepresentation() {
        hmim.eteam.rest.backend.model.CreativeTaskAnswer answer =
                new hmim.eteam.rest.backend.model.CreativeTaskAnswer().
                        id(getId()).
                        text(getAnswer()).
                        participant(student.getId()).
                        task(task.getId()).
                        date(date.toInstant().atOffset(ZoneOffset.UTC));

        grades.forEach(grade -> answer.addEvaluationsItem(grade.toApiRepresentation()));
        return answer;
    }
}
