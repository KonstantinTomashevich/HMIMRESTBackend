package hmim.eteam.rest.backend.entity.forum;

import hmim.eteam.rest.backend.entity.core.User;

import javax.persistence.*;

@Entity
public class ForumMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private ForumTheme theme;

    @ManyToOne
    private User user;

    private Long index;

    private String text;

    public ForumMessage() {
    }

    public ForumMessage(ForumTheme theme, User user, Long index, String text) {
        this.theme = theme;
        this.user = user;
        this.index = index;
        this.text = text;
    }

    public Long getId() {
        return id;
    }

    public ForumTheme getTheme() {
        return theme;
    }

    public User getUser() {
        return user;
    }

    public Long getIndex() {
        return index;
    }

    public String getText() {
        return text;
    }
}
