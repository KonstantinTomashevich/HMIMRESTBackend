package hmim.eteam.rest.backend.entity.user;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class AuthToken {
    @Id
    @NotNull
    private String id;

    @ManyToOne
    @NotNull
    private SiteUser siteUser;

    @NotNull
    private Date expireDate;

    public AuthToken() {
    }

    public AuthToken(String id, SiteUser siteUser, Date expireDate) {
        this.id = id;
        this.siteUser = siteUser;
        this.expireDate = expireDate;
    }

    public String getId() {
        return id;
    }

    public SiteUser getUser() {
        return siteUser;
    }

    public Date getExpireDate() {
        return expireDate;
    }
}
