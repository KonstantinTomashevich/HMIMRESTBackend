package hmim.eteam.rest.backend.repository.test;

import hmim.eteam.rest.backend.entity.test.TestAnswer;
import hmim.eteam.rest.backend.entity.test.TestQuestion;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(exported = false)
public interface TestAnswerRepository extends CrudRepository<TestAnswer, Long> {
    List<TestAnswer> findByQuestionOrderByPriority(TestQuestion question);
}
