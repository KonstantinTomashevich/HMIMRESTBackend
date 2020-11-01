package hmim.eteam.rest.backend.entity.forum;

import hmim.eteam.rest.backend.entity.core.SiteUser;

import javax.persistence.*;

@Entity
public class ForumMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private ForumTheme theme;

    @ManyToOne
    private SiteUser siteUser;

    private Long index;

    private String text;

    public ForumMessage() {
    }

    public ForumMessage(ForumTheme theme, SiteUser siteUser, Long index, String text) {
        this.theme = theme;
        this.siteUser = siteUser;
        this.index = index;
        this.text = text;
    }

    public Long getId() {
        return id;
    }

    public ForumTheme getTheme() {
        return theme;
    }

    public SiteUser getUser() {
        return siteUser;
    }

    public Long getIndex() {
        return index;
    }

    public String getText() {
        return text;
    }
}
