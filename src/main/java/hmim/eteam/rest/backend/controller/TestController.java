package hmim.eteam.rest.backend.controller;

import hmim.eteam.rest.backend.api.IRoleResolver;
import hmim.eteam.rest.backend.entity.test.TestResult;
import hmim.eteam.rest.backend.entity.test.TestUserAnswer;
import hmim.eteam.rest.backend.entity.user.AuthToken;
import hmim.eteam.rest.backend.entity.user.UserRole;
import hmim.eteam.rest.backend.model.TestAnswer;
import hmim.eteam.rest.backend.repository.test.TestResultRepository;
import hmim.eteam.rest.backend.repository.test.TestUserAnswerRepository;
import hmim.eteam.rest.backend.repository.user.AuthTokenRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TestController {
    private final IRoleResolver roleResolver;
    private final TestResultRepository testResultRepository;
    private final TestUserAnswerRepository testUserAnswerRepository;
    private final AuthTokenRepository authTokenRepository;

    public TestController(IRoleResolver roleResolver, TestResultRepository testResultRepository,
                          TestUserAnswerRepository testUserAnswerRepository,
                          AuthTokenRepository authTokenRepository) {
        this.roleResolver = roleResolver;
        this.testResultRepository = testResultRepository;
        this.testUserAnswerRepository = testUserAnswerRepository;
        this.authTokenRepository = authTokenRepository;
    }

    public ResponseEntity<List<TestAnswer>> testResultsResultIdAnswersGet(String token, Integer resultId) {
        if (token == null || resultId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Optional<TestResult> testResult = testResultRepository.findById((long) resultId);
        if (!testResult.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        UserRole role = roleResolver.resolve(token, testResult.get().getTest().getTheme().getCourse().getId());
        if (role != UserRole.Admin) {
            Optional<AuthToken> authToken = authTokenRepository.resolveToken(token);
            if (!authToken.isPresent() ||
                    !authToken.get().getUser().getId().equals(testResult.get().getUser().getId())) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        }

        List<TestUserAnswer> answers = testUserAnswerRepository.findByResult(testResult.get());
        List<TestAnswer> convertedAnswers = new ArrayList<>();
        answers.forEach(answer -> convertedAnswers.add(answer.toApiRepresentation()));
        return new ResponseEntity<>(convertedAnswers, HttpStatus.OK);
    }
}
