package hmim.eteam.rest.backend.representation.output;

import hmim.eteam.rest.backend.entity.user.SiteUser;

public class UserInfo {
    public Long id;
    public String name;

    public UserInfo(SiteUser user) {
        id = user.getId();
        name = user.getVisibleName();
    }
}
