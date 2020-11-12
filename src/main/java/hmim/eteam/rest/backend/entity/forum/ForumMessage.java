package hmim.eteam.rest.backend.entity.forum;

import hmim.eteam.rest.backend.entity.user.SiteUser;
import hmim.eteam.rest.backend.entity.util.IdentifiedEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Objects;

@Entity
public class ForumMessage extends IdentifiedEntity {
    @ManyToOne
    @NotNull
    private ForumTheme theme;

    @ManyToOne
    @NotNull
    private SiteUser siteUser;

    @NotNull
    private Date date;

    @NotNull
    private String text;

    public ForumMessage() {
    }

    public ForumMessage(ForumTheme theme, SiteUser siteUser, Date date, String text) {
        this.theme = theme;
        this.siteUser = siteUser;
        this.date = date;
        this.text = text;
    }

    public ForumTheme getTheme() {
        return theme;
    }

    public SiteUser getUser() {
        return siteUser;
    }

    public Date getDate() {
        return date;
    }

    public String getText() {
        return text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ForumMessage that = (ForumMessage) o;
        return getId().equals(that.getId()) &&
                theme.equals(that.theme) &&
                Objects.equals(siteUser, that.siteUser) &&
                date.equals(that.date) &&
                text.equals(that.text);
    }
}
