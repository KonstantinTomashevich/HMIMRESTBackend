package hmim.eteam.rest.backend.entity.core;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class SiteUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id = null;

    private String visibleName;

    private String loginMD5;

    private String passwordMD5;

    private Boolean isGlobalAdministrator;

    public SiteUser() {

    }

    public SiteUser(String visibleName, String loginMD5, String passwordMD5, Boolean isGlobalAdministrator) {
        this.visibleName = visibleName;
        this.loginMD5 = loginMD5;
        this.passwordMD5 = passwordMD5;
        this.isGlobalAdministrator = isGlobalAdministrator;
    }

    public Long getId() {
        return id;
    }

    public String getVisibleName() {
        return visibleName;
    }

    public String getLoginMD5() {
        return loginMD5;
    }

    public String getPasswordMD5() {
        return passwordMD5;
    }

    public Boolean isGlobalAdministrator() {
        return isGlobalAdministrator;
    }
}
