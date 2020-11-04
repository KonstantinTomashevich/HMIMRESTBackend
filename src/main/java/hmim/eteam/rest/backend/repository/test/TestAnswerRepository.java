package hmim.eteam.rest.backend.repository.test;

import hmim.eteam.rest.backend.entity.test.TestAnswer;
import hmim.eteam.rest.backend.entity.test.TestQuestion;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TestAnswerRepository extends CrudRepository<TestAnswer, Long> {
    List<TestAnswer> findByQuestion(TestQuestion question);
}
