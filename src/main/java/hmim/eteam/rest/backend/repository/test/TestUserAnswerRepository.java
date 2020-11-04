package hmim.eteam.rest.backend.repository.test;

import hmim.eteam.rest.backend.entity.core.SiteUser;
import hmim.eteam.rest.backend.entity.test.TestResult;
import hmim.eteam.rest.backend.entity.test.TestUserAnswer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TestUserAnswerRepository extends CrudRepository<TestUserAnswer, Long> {
    List<TestUserAnswer> findBySiteUserAndResult(SiteUser siteUser, TestResult result);
}
