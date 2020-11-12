package hmim.eteam.rest.backend.repository.test;

import hmim.eteam.rest.backend.entity.test.Test;
import hmim.eteam.rest.backend.entity.test.TestQuestion;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TestQuestionRepository extends CrudRepository<TestQuestion, Long> {
    List<TestQuestion> findByTestOrderByPriorityAsc(Test test);
}
