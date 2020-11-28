package hmim.eteam.rest.backend.repository.test;

import hmim.eteam.rest.backend.entity.user.SiteUser;
import hmim.eteam.rest.backend.entity.test.TestResult;
import hmim.eteam.rest.backend.entity.test.TestUserAnswer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(exported = false)
public interface TestUserAnswerRepository extends CrudRepository<TestUserAnswer, Long> {
    List<TestUserAnswer> findByResult(TestResult result);
}
