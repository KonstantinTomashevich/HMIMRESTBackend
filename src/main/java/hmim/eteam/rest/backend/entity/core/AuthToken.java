package hmim.eteam.rest.backend.entity.core;

import javax.persistence.*;
import java.util.Date;

@Entity
public class AuthToken {
    @Id
    private String id;

    @ManyToOne
    private SiteUser siteUser;

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
