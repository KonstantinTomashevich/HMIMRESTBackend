package hmim.eteam.rest.backend.entity.task;

import hmim.eteam.rest.backend.entity.core.User;

import javax.persistence.*;

@Entity
public class CreativeTaskAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private CreativeTask task;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String answer;

    private Integer grade;

    public CreativeTaskAnswer() {
    }

    public CreativeTaskAnswer(CreativeTask task, User user, String answer, Integer grade) {
        this.task = task;
        this.user = user;
        this.answer = answer;
        this.grade = grade;
    }

    public Long getId() {
        return id;
    }

    public CreativeTask getTask() {
        return task;
    }

    public User getUser() {
        return user;
    }

    public String getAnswer() {
        return answer;
    }

    public Integer getGrade() {
        return grade;
    }
}
