package hmim.eteam.rest.backend.controller;

import hmim.eteam.rest.backend.api.IRoleResolver;
import hmim.eteam.rest.backend.entity.test.Test;
import hmim.eteam.rest.backend.entity.test.TestResult;
import hmim.eteam.rest.backend.entity.test.TestUserAnswer;
import hmim.eteam.rest.backend.entity.user.AuthToken;
import hmim.eteam.rest.backend.entity.user.UserRole;
import hmim.eteam.rest.backend.model.TestAnswer;
import hmim.eteam.rest.backend.model.TestQuestion;
import hmim.eteam.rest.backend.repository.test.TestQuestionRepository;
import hmim.eteam.rest.backend.repository.test.TestRepository;
import hmim.eteam.rest.backend.repository.test.TestResultRepository;
import hmim.eteam.rest.backend.repository.test.TestUserAnswerRepository;
import hmim.eteam.rest.backend.repository.user.AuthTokenRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TestController {
    private final IRoleResolver roleResolver;
    private final TestRepository testRepository;
    private final TestResultRepository testResultRepository;
    private final TestQuestionRepository testQuestionRepository;
    private final TestUserAnswerRepository testUserAnswerRepository;
    private final AuthTokenRepository authTokenRepository;

    public TestController(IRoleResolver roleResolver, TestRepository testRepository,
                          TestResultRepository testResultRepository,
                          TestQuestionRepository testQuestionRepository,
                          TestUserAnswerRepository testUserAnswerRepository,
                          AuthTokenRepository authTokenRepository) {
        this.testRepository = testRepository;
        this.roleResolver = roleResolver;
        this.testResultRepository = testResultRepository;
        this.testQuestionRepository = testQuestionRepository;
        this.testUserAnswerRepository = testUserAnswerRepository;
        this.authTokenRepository = authTokenRepository;
    }

    public ResponseEntity<List<TestAnswer>> testResultsResultIdAnswersGet(@NotNull String token, @NotNull Long resultId) {
        Optional<TestResult> testResult = testResultRepository.findById(resultId);
        if (!testResult.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Optional<AuthToken> authToken = authTokenRepository.resolveToken(token);
        UserRole role = roleResolver.resolve(authToken, testResult.get().getTest().getTheme().getCourse().getId());

        if (role != UserRole.Admin && (!authToken.isPresent() ||
                !authToken.get().getUser().getId().equals(testResult.get().getUser().getId()))) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        List<TestUserAnswer> answers = testUserAnswerRepository.findByResult(testResult.get());
        List<TestAnswer> convertedAnswers = new ArrayList<>();
        answers.forEach(answer -> convertedAnswers.add(answer.toApiRepresentation()));
        return new ResponseEntity<>(convertedAnswers, HttpStatus.OK);
    }

    public ResponseEntity<List<hmim.eteam.rest.backend.model.TestResult>> testIdResultsGet(@NotNull String token, @NotNull Long testId, Long participant) {
        Optional<Test> test = testRepository.findById(testId);
        if (!test.isPresent()) {
            Logger.getLogger(getClass().getSimpleName()).log(Level.INFO,
                    String.format("Unable to find test with id %d!", testId));
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Optional<AuthToken> authToken = authTokenRepository.resolveToken(token);
        if (!authToken.isPresent()) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        UserRole role = roleResolver.resolve(authToken, test.get().getTheme().getCourse().getId());
        if (role != UserRole.Admin && !authToken.get().getUser().getId().equals(participant)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        List<TestResult> results;
        if (participant == null) {
            results = testResultRepository.findByTestOrderByFinishDateAsc(test.get());
        } else {
            results = testResultRepository.findBySiteUserAndTestOrderByFinishDateAsc(
                    authToken.get().getUser(), test.get());
        }

        List<hmim.eteam.rest.backend.model.TestResult> convertedResults = new ArrayList<>();
        results.forEach(result -> convertedResults.add(result.toApiRepresentation()));
        return new ResponseEntity<>(convertedResults, HttpStatus.OK);
    }

    public ResponseEntity<List<TestQuestion>> testQuestions(@NotNull String token, @NotNull Long testId) {
        Optional<Test> test = testRepository.findById(testId);
        if (!test.isPresent()) {
            Logger.getLogger(getClass().getSimpleName()).log(Level.INFO,
                    String.format("Unable to find test with id %d!", testId));
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        UserRole role = roleResolver.resolve(token, test.get().getTheme().getCourse().getId());
        if (role == UserRole.Guest) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        List<TestQuestion> questions = new ArrayList<>();
        testQuestionRepository.findByTestOrderByPriorityAsc(test.get()).
                forEach(question -> questions.add(question.toApiRepresentation()));
        return new ResponseEntity<>(questions, HttpStatus.OK);
    }
}
