package hmim.eteam.rest.backend.entity.task;

import hmim.eteam.rest.backend.entity.core.SiteUser;

import javax.persistence.*;

@Entity
public class CreativeTaskAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private CreativeTask task;

    @ManyToOne
    private SiteUser siteUser;

    private String answer;

    private Integer grade;

    public CreativeTaskAnswer() {
    }

    public CreativeTaskAnswer(CreativeTask task, SiteUser siteUser, String answer, Integer grade) {
        this.task = task;
        this.siteUser = siteUser;
        this.answer = answer;
        this.grade = grade;
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
}
