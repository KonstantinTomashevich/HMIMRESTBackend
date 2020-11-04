package hmim.eteam.rest.backend.entity.test;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class TestQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @NotNull
    private Test test;

    @NotNull
    private String text;

    @NotNull
    private Long index;

    public TestQuestion() {
    }

    public TestQuestion(Test test, String text, long index) {
        this.test = test;
        this.text = text;
        this.index = index;
    }

    public Long getId() {
        return id;
    }

    public Test getTest() {
        return test;
    }

    public String getText() {
        return text;
    }

    public long getIndex() {
        return index;
    }
}
