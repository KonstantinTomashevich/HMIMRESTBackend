package hmim.eteam.rest.backend.repository.task;

import hmim.eteam.rest.backend.entity.user.SiteUser;
import hmim.eteam.rest.backend.entity.task.CreativeTask;
import hmim.eteam.rest.backend.entity.task.CreativeTaskAnswer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(exported = false)
public interface CreativeTaskAnswerRepository extends CrudRepository<CreativeTaskAnswer, Long> {
    List<CreativeTaskAnswer> findByStudentAndTaskOrderByDateAsc(SiteUser siteUser, CreativeTask task);
}
