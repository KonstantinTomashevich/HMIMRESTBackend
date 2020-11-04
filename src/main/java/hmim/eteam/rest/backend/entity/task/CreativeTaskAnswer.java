package hmim.eteam.rest.backend.entity.task;

import hmim.eteam.rest.backend.entity.core.SiteUser;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class CreativeTaskAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @NotNull
    private CreativeTask task;

    @ManyToOne
    @NotNull
    private SiteUser siteUser;

    @NotNull
    private String answer;

    @NotNull
    private Integer grade;

    @NotNull
    private Date date;

    public CreativeTaskAnswer() {
    }

    public CreativeTaskAnswer(CreativeTask task, SiteUser siteUser, String answer, Integer grade, Date date) {
        this.task = task;
        this.siteUser = siteUser;
        this.answer = answer;
        this.grade = grade;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public CreativeTask getTask() {
        return task;
    }

    public SiteUser getUser() {
        return siteUser;
    }

    public String getAnswer() {
        return answer;
    }

    public Integer getGrade() {
        return grade;
    }

    public Date getDate() {
        return date;
    }
}
