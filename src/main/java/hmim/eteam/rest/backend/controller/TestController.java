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
import org.javatuples.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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

    public ResponseEntity<List<TestAnswer>> testResultsResultIdAnswersGet(String token, Long resultId) {
        if (token == null || resultId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

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

    private Pair<HttpStatus, Test> accessTestRoutine(Long testId) {
        if (testId == null) {
            Logger.getLogger(getClass().getSimpleName()).log(Level.INFO, "Bad request: no test id!");
            return new Pair<>(HttpStatus.BAD_REQUEST, null);
        }

        Optional<Test> test = testRepository.findById(testId);
        if (!test.isPresent()) {
            Logger.getLogger(getClass().getSimpleName()).log(Level.INFO,
                    String.format("Unable to find test with id %d!", testId));
            return new Pair<>(HttpStatus.NOT_FOUND, null);
        }

        return new Pair<>(HttpStatus.OK, test.get());
    }

    public ResponseEntity<List<hmim.eteam.rest.backend.model.TestResult>> testIdResultsGet(String token, Long testId, Long participant) {
        if (token == null) {
            Logger.getLogger(getClass().getSimpleName()).log(Level.INFO, "Bad request: no token");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Pair<HttpStatus, Test> testPair = accessTestRoutine(testId);
        if (testPair.getValue0() != HttpStatus.OK) {
            return new ResponseEntity<>(testPair.getValue0());
        }

        Optional<AuthToken> authToken = authTokenRepository.resolveToken(token);
        if (!authToken.isPresent()) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        UserRole role = roleResolver.resolve(authToken, testPair.getValue1().getTheme().getCourse().getId());
        if (role != UserRole.Admin && !authToken.get().getUser().getId().equals(participant)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        List<TestResult> results;
        if (participant == null) {
            results = testResultRepository.findByTestOrderByFinishDateAsc(testPair.getValue1());
        } else {
            results = testResultRepository.findBySiteUserAndTestOrderByFinishDateAsc(
                    authToken.get().getUser(), testPair.getValue1());
        }

        List<hmim.eteam.rest.backend.model.TestResult> convertedResults = new ArrayList<>();
        results.forEach(result -> convertedResults.add(result.toApiRepresentation()));
        return new ResponseEntity<>(convertedResults, HttpStatus.OK);
    }

    public ResponseEntity<List<TestQuestion>> testQuestions(String token, Long testId) {
        if (token == null) {
            Logger.getLogger(getClass().getSimpleName()).log(Level.INFO, "Bad request: no token!");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Pair<HttpStatus, Test> testPair = accessTestRoutine(testId);
        if (testPair.getValue0() != HttpStatus.OK) {
            return new ResponseEntity<>(testPair.getValue0());
        }

        UserRole role = roleResolver.resolve(token, testPair.getValue1().getTheme().getCourse().getId());
        if (role == UserRole.Guest) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        List<TestQuestion> questions = new ArrayList<>();
        testQuestionRepository.findByTestOrderByPriorityAsc(testPair.getValue1()).
                forEach(question -> questions.add(question.toApiRepresentation()));
        return new ResponseEntity<>(questions, HttpStatus.OK);
    }
}
