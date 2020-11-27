package hmim.eteam.rest.backend.api;

import hmim.eteam.rest.backend.controller.AuthController;
import hmim.eteam.rest.backend.controller.CourseController;
import hmim.eteam.rest.backend.controller.CreativeTaskController;
import hmim.eteam.rest.backend.controller.UserController;
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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Controller
public class Router implements DefaultApi {
    private final AuthController authController;
    private final CourseController courseController;
    private final UserController userController;
    private final CreativeTaskController creativeTaskController;

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
    }

    @Override
    public ResponseEntity<List<CourseRole>> courseRoles(String token, @NotNull @Valid Integer id) {
        return courseController.courseRoles(token, id);
    }

    @Override
    public ResponseEntity<List<Course>> courses(String token) {
        return courseController.courses(token);
    }

    @Override
    public ResponseEntity<AuthenticationToken> registration(@Valid UserRegistrationInfo userRegistrationInfo) {
        return authController.register(userRegistrationInfo);
    }

    @Override
    public ResponseEntity<SelfInfo> me(String token) {
        return userController.me(token);
    }

    @Override
    public ResponseEntity<AuthenticationToken> login(@Valid UserLoginInfo userLoginInfo) {
        return authController.login(userLoginInfo);
    }

    @Override
    public ResponseEntity<List<CreativeTaskAnswer>> taskIdAnswersGet(String token, Integer id, @Valid Integer participant) {
        return creativeTaskController.taskIdAnswersGet(token, id, participant);
    }
}
