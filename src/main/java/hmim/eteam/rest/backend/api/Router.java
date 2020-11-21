package hmim.eteam.rest.backend.api;

import hmim.eteam.rest.backend.controller.AuthController;
import hmim.eteam.rest.backend.model.AuthenticationToken;
import hmim.eteam.rest.backend.model.UserLoginInfo;
import hmim.eteam.rest.backend.model.UserRegistrationInfo;
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

@Controller
public class Router implements DefaultApi {
    private final AuthController authController;

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

        authController = new AuthController(siteUserRepository, authTokenRepository);
    }

    @Override
    public ResponseEntity<AuthenticationToken> registration(@Valid UserRegistrationInfo userRegistrationInfo) {
        return authController.register(userRegistrationInfo);
    }

    @Override
    public ResponseEntity<AuthenticationToken> login(@Valid UserLoginInfo userLoginInfo) {
        return authController.login(userLoginInfo);
    }
}
