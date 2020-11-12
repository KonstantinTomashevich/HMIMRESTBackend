package hmim.eteam.rest.backend.entity.task;

import hmim.eteam.rest.backend.entity.core.SiteUser;
import hmim.eteam.rest.backend.entity.util.IdentifiedEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class CreativeTaskGrade extends IdentifiedEntity {
    @ManyToOne
    @NotNull
    private CreativeTaskAnswer answer;

    @NotNull
    private Integer grade;

    private String comment;

    @ManyToOne
    @NotNull
    private SiteUser teacher;

    public CreativeTaskGrade() {
    }

    public CreativeTaskGrade(CreativeTaskAnswer answer, Integer grade, String comment, SiteUser teacher) {
        this.answer = answer;
        this.grade = grade;
        this.comment = comment;
        this.teacher = teacher;
    }

    public CreativeTaskAnswer getAnswer() {
        return answer;
    }

    public Integer getGrade() {
        return grade;
    }

    public String getComment() {
        return comment;
    }

    public SiteUser getTeacher() {
        return teacher;
    }
}
