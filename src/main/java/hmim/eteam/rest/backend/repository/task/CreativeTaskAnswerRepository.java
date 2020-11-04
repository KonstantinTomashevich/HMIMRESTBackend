package hmim.eteam.rest.backend.repository.task;

import hmim.eteam.rest.backend.entity.core.SiteUser;
import hmim.eteam.rest.backend.entity.task.CreativeTask;
import hmim.eteam.rest.backend.entity.task.CreativeTaskAnswer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CreativeTaskAnswerRepository extends CrudRepository<CreativeTaskAnswer, Long> {
    List<CreativeTaskAnswer> findBySiteUserAndTaskOrderByDateAsc(SiteUser siteUser, CreativeTask task);
}
