package hmim.eteam.rest.backend.entity.user;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AuthToken)) return false;
        AuthToken authToken = (AuthToken) o;
        return id.equals(authToken.id) &&
                siteUser.equals(authToken.siteUser) &&
                expireDate.equals(authToken.expireDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, siteUser, expireDate);
    }
}
