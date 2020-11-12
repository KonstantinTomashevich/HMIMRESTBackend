package hmim.eteam.rest.backend.entity.core;

import hmim.eteam.rest.backend.entity.IdentifiedEntity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class SiteUser extends IdentifiedEntity {
    @NotNull
    private String visibleName;

    @NotNull
    private String loginMD5;

    @NotNull
    private String passwordMD5;

    @NotNull
    private Boolean isGlobalAdministrator;

    public SiteUser() {

    }

    public SiteUser(String visibleName, String loginMD5, String passwordMD5, boolean isGlobalAdministrator) {
        this.visibleName = visibleName;
        this.loginMD5 = loginMD5;
        this.passwordMD5 = passwordMD5;
        this.isGlobalAdministrator = isGlobalAdministrator;
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

    public boolean isGlobalAdministrator() {
        return isGlobalAdministrator;
    }
}
