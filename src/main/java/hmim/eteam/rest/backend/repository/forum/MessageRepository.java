package hmim.eteam.rest.backend.repository.forum;

import hmim.eteam.rest.backend.entity.forum.ForumMessage;
import hmim.eteam.rest.backend.entity.forum.ForumTheme;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(exported = false)
public interface MessageRepository extends CrudRepository<ForumMessage, Long> {
    List<ForumMessage> findByThemeOrderByDateAsc(ForumTheme theme);
    ForumMessage findTopByThemeOrderByDateDesc(ForumTheme theme);
}
