package hmim.eteam.rest.backend.entity.core;

import javax.persistence.*;
import java.util.Date;

@Entity
public class AuthToken {
    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Date expireDate;

    public AuthToken() {
    }

    public AuthToken(String id, User user, Date expireDate) {
        this.id = id;
        this.user = user;
        this.expireDate = expireDate;
    }

    public String getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Date getExpireDate() {
        return expireDate;
    }
}
