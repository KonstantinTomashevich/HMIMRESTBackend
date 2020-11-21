package hmim.eteam.rest.backend.repository.test;

import hmim.eteam.rest.backend.entity.test.Test;
import hmim.eteam.rest.backend.entity.test.TestQuestion;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(exported = false)
public interface TestQuestionRepository extends CrudRepository<TestQuestion, Long> {
    List<TestQuestion> findByTestOrderByPriorityAsc(Test test);
}
