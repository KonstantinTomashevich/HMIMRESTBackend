package hmim.eteam.rest.backend.api;

import hmim.eteam.rest.backend.controller.*;
import hmim.eteam.rest.backend.model.*;
import hmim.eteam.rest.backend.repository.course.CourseRepository;
import hmim.eteam.rest.backend.repository.course.CourseThemeRepository;
import hmim.eteam.rest.backend.repository.forum.MessageRepository;
import hmim.eteam.rest.backend.repository.forum.ThemeRepository;
import hmim.eteam.rest.backend.repository.learning.ImageMaterialRepository;
import hmim.eteam.rest.backend.repository.learning.TextMaterialRepository;
import hmim.eteam.rest.backend.repository.learning.VideoMaterialRepository;
import hmim.eteam.rest.backend.repository.task.CreativeTaskAnswerRepository;
import hmim.eteam.rest.backend.repository.task.CreativeTaskRepository;
import hmim.eteam.rest.backend.repository.test.*;
import hmim.eteam.rest.backend.repository.user.AuthTokenRepository;
import hmim.eteam.rest.backend.repository.user.CourseRoleRepository;
import hmim.eteam.rest.backend.repository.user.SiteUserRepository;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Controller
public class Router implements DefaultApi {
    private final AuthController authController;
    private final CourseController courseController;
    private final UserController userController;
    private final CreativeTaskController creativeTaskController;
    private final TestController testController;

    public Router(CourseRepository courseRepository, CourseThemeRepository courseThemeRepository,
                  MessageRepository forumMessageRepository, ThemeRepository forumThemeRepository,
                  ImageMaterialRepository imageMaterialRepository, TextMaterialRepository textMaterialRepository,
                  VideoMaterialRepository videoMaterialRepository,
                  CreativeTaskAnswerRepository creativeTaskAnswerRepository,
                  CreativeTaskRepository creativeTaskRepository,
                  TestAnswerRepository testAnswerRepository, TestQuestionRepository testQuestionRepository,
                  TestRepository testRepository, TestResultRepository testResultRepository,
                  TestUserAnswerRepository testUserAnswerRepository, AuthTokenRepository authTokenRepository,
                  CourseRoleRepository courseRoleRepository, SiteUserRepository siteUserRepository) {

        IRoleResolver roleResolver = new RoleResolver(authTokenRepository, courseRepository, courseRoleRepository);
        authController = new AuthController(siteUserRepository, authTokenRepository);
        courseController = new CourseController(roleResolver, courseRepository, courseRoleRepository);
        userController = new UserController(authTokenRepository);

        creativeTaskController = new CreativeTaskController(roleResolver, creativeTaskRepository,
                creativeTaskAnswerRepository, authTokenRepository, siteUserRepository);

        testController = new TestController(roleResolver, testRepository, testResultRepository,
                testQuestionRepository, testUserAnswerRepository, authTokenRepository);
    }

    @ApiOperation(value = "Retrieve course roles (admin only)", nickname = "courseRoles", notes = "Retrieve course roles for all participants", response = CourseRole.class, responseContainer = "List", tags = {})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved course roles", response = CourseRole.class, responseContainer = "List"),
            @ApiResponse(code = 400, message = "Invalid course id was provided"),
            @ApiResponse(code = 404, message = "Course roles not found")})
    @RequestMapping(value = "/course_roles",
            produces = {"application/json"},
            method = RequestMethod.GET)
    @Override
    public ResponseEntity<List<CourseRole>> courseRoles(
            @ApiParam(value = "Authentication token", required = true) @RequestHeader(value = "token") String token,
            @NotNull @ApiParam(value = "Course id", required = true) @Valid @RequestParam(value = "id") Long id) {
        return courseController.courseRoles(token, id);
    }

    @ApiOperation(value = "Retrieve courses information", nickname = "courses", notes = "Retrieve information about courses without fetching materials", response = Course.class, responseContainer = "List", tags = {})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved courses", response = Course.class, responseContainer = "List"),
            @ApiResponse(code = 400, message = "Unexpected error occcurred")})
    @RequestMapping(value = "/courses",
            produces = {"application/json"},
            method = RequestMethod.GET)
    @Override
    public ResponseEntity<List<Course>> courses(
            @ApiParam(value = "Authentication token", required = true) @RequestHeader(value = "token") String token) {
        return courseController.courses(token);
    }

    @ApiOperation(value = "Register new user", nickname = "registration", notes = "The process when a user provide new", response = AuthenticationToken.class, tags = {})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully registered", response = AuthenticationToken.class),
            @ApiResponse(code = 409, message = "The login or password is already used")})
    @RequestMapping(value = "/register",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    @Override
    public ResponseEntity<AuthenticationToken> registration(@ApiParam() @Valid @RequestBody UserRegistrationInfo userRegistrationInfo) {
        return authController.register(userRegistrationInfo);
    }

    @ApiOperation(value = "Retrieve self information", nickname = "me", response = SelfInfo.class, tags = {})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved information about self", response = SelfInfo.class),
            @ApiResponse(code = 404, message = "Info about the user wasn't found")})
    @RequestMapping(value = "/me",
            produces = {"application/json"},
            method = RequestMethod.GET)
    @Override
    public ResponseEntity<SelfInfo> me(
            @ApiParam(value = "Authentication token", required = true) @RequestHeader(value = "token") String token) {
        return userController.me(token);
    }

    @ApiOperation(value = "Log in account", nickname = "login", notes = "The process when a user logs in an account", response = AuthenticationToken.class, tags = {})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully logged in", response = AuthenticationToken.class),
            @ApiResponse(code = 400, message = "Invalid status value")})
    @RequestMapping(value = "/login",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    @Override
    public ResponseEntity<AuthenticationToken> login(@ApiParam() @Valid @RequestBody UserLoginInfo userLoginInfo) {
        return authController.login(userLoginInfo);
    }

    @ApiOperation(value = "Retrieve answers of the task for specified user", nickname = "taskIdAnswersGet", response = CreativeTaskAnswer.class, responseContainer = "List", tags = {})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved answer(s) for specific task for specific user (if nil - return all answers for all users)", response = CreativeTaskAnswer.class, responseContainer = "List")})
    @RequestMapping(value = "/task/{id}/answers",
            produces = {"application/json"},
            method = RequestMethod.GET)
    @Override
    public ResponseEntity<List<CreativeTaskAnswer>> taskIdAnswersGet(
            @ApiParam(value = "Authentication token", required = true) @RequestHeader(value = "token") String token,
            @ApiParam(value = "Task id", required = true) @PathVariable("id") Long id,
            @ApiParam(value = "Participant id") @Valid @RequestParam(value = "participant", required = false) Long participant) {
        return creativeTaskController.taskIdAnswersGet(token, id, participant);
    }

    @ApiOperation(value = "Retrieve user answers of the test in specific attempt", nickname = "testResultsResultIdAnswersGet", response = TestAnswer.class, responseContainer = "List", tags = {})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved answers for specific test attempt.", response = TestAnswer.class, responseContainer = "List")})
    @RequestMapping(value = "/test/results/{resultId}/answers/",
            produces = {"application/json"},
            method = RequestMethod.GET)
    @Override
    public ResponseEntity<List<TestAnswer>> testResultsResultIdAnswersGet(
            @ApiParam(value = "Authentication token", required = true) @RequestHeader(value = "token") String token,
            @ApiParam(value = "Test result id", required = true) @PathVariable("resultId") Long resultId) {
        return testController.testResultsResultIdAnswersGet(token, resultId);
    }

    @ApiOperation(value = "Retrieve answers of the test for specified user", nickname = "testIdResultsGet", response = TestResult.class, responseContainer = "List", tags = {})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved result(s) for specific test for specific user (if nil - return all results for all users)", response = TestResult.class, responseContainer = "List")})
    @RequestMapping(value = "/test/{id}/results",
            produces = {"application/json"},
            method = RequestMethod.GET)
    public ResponseEntity<List<TestResult>> testIdResultsGet(
            @ApiParam(value = "Authentication token", required = true) @RequestHeader(value = "token") String token,
            @ApiParam(value = "Test id", required = true) @PathVariable("id") Long id,
            @ApiParam(value = "Participant id") @Valid @RequestParam(value = "participant", required = false) Long participant) {
        return testController.testIdResultsGet(token, id, participant);
    }

    @ApiOperation(value = "Retrieve test questions", nickname = "testQuestions", response = TestQuestion.class, responseContainer = "List", tags = {})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved questions by test id", response = TestQuestion.class, responseContainer = "List")})
    @RequestMapping(value = "/test/{id}/questions",
            produces = {"application/json"},
            method = RequestMethod.GET)
    @Override
    public ResponseEntity<List<TestQuestion>> testQuestions(
            @ApiParam(value = "Authentication token", required = true) @RequestHeader(value = "token") String token,
            @ApiParam(value = "Test id", required = true) @PathVariable("id") Long id) {
        return testController.testQuestions(token, id);
    }
}
