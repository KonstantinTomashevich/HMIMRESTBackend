package hmim.eteam.rest.backend.representation.output;

import hmim.eteam.rest.backend.entity.forum.ForumMessage;

import java.util.Date;

public class MessageInfo {
    public Long userId;
    public String text;
    public Date date;

    public MessageInfo(ForumMessage message) {
        userId = message.getUser().getId();
        text = message.getText();
        date = message.getDate();
    }
}
