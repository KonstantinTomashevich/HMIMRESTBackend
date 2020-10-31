package hmim.eteam.rest.backend.entity.test;

import javax.persistence.*;

@Entity
public class TestQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Test test;

    private String text;

    public TestQuestion() {
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
}
