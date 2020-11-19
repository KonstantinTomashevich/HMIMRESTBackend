package hmim.eteam.rest.backend.representation.output;

import hmim.eteam.rest.backend.entity.forum.ForumTheme;

import java.util.Date;

public class ThemeInfo {
    public Long id;
    public String name;
    public Date lastUpdated;

    public ThemeInfo(ForumTheme theme) {
        id = theme.getId();
        name = theme.getName();
        lastUpdated = theme.getLastUpdateDate();
    }
}
