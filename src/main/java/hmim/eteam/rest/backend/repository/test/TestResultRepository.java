package hmim.eteam.rest.backend.repository.test;

import hmim.eteam.rest.backend.entity.user.SiteUser;
import hmim.eteam.rest.backend.entity.test.Test;
import hmim.eteam.rest.backend.entity.test.TestResult;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TestResultRepository extends CrudRepository<TestResult, Long> {
    List<TestResult> findBySiteUserAndTestOrderByFinishDateAsc(SiteUser siteUser, Test test);
}
