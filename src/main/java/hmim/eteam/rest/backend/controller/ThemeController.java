package hmim.eteam.rest.backend.controller;

import hmim.eteam.rest.backend.api.IRoleResolver;
import hmim.eteam.rest.backend.entity.course.CourseTheme;
import hmim.eteam.rest.backend.entity.user.UserRole;
import hmim.eteam.rest.backend.model.*;
import hmim.eteam.rest.backend.repository.course.CourseRepository;
import hmim.eteam.rest.backend.repository.course.CourseThemeRepository;
import hmim.eteam.rest.backend.repository.course.ThemeStatusRepository;
import hmim.eteam.rest.backend.repository.learning.ImageMaterialRepository;
import hmim.eteam.rest.backend.repository.learning.TextMaterialRepository;
import hmim.eteam.rest.backend.repository.learning.VideoMaterialRepository;
import hmim.eteam.rest.backend.repository.task.CreativeTaskRepository;
import hmim.eteam.rest.backend.repository.test.TestQuestionRepository;
import hmim.eteam.rest.backend.repository.test.TestRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ThemeController {
    private final IRoleResolver roleResolver;
    private final CourseThemeRepository themeRepository;
    private final ImageMaterialRepository imageMaterialRepository;
    private final VideoMaterialRepository videoMaterialRepository;
    private final TextMaterialRepository textMaterialRepository;
    private final TestRepository testRepository;
    private final CreativeTaskRepository creativeTaskRepository;
    private final ThemeStatusRepository themeStatusRepository;

    // Adhook
    private final CourseRepository courseRepository;
    private final TestQuestionRepository testQuestionRepository;

    public ThemeController(IRoleResolver roleResolver,
                           CourseThemeRepository themeRepository,
                           ImageMaterialRepository imageMaterialRepository,
                           VideoMaterialRepository videoMaterialRepository,
                           TestRepository testRepository,
                           TextMaterialRepository textMaterialRepository,
                           CreativeTaskRepository creativeTaskRepository,
                           ThemeStatusRepository themeStatusRepository,
                           CourseRepository courseRepository,
                           TestQuestionRepository testQuestionRepository) {
        this.roleResolver = roleResolver;
        this.themeRepository = themeRepository;
        this.imageMaterialRepository = imageMaterialRepository;
        this.videoMaterialRepository = videoMaterialRepository;
        this.textMaterialRepository = textMaterialRepository;
        this.testRepository = testRepository;
        this.creativeTaskRepository = creativeTaskRepository;
        this.themeStatusRepository = themeStatusRepository;
        this.courseRepository = courseRepository;
        this.testQuestionRepository = testQuestionRepository;
    }

    public ResponseEntity<List<ImageMaterial>> themeImages(@NotNull String token, @NotNull Long themeId) {
        Optional<CourseTheme> theme = themeRepository.findById(themeId);
        if (!theme.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<ImageMaterial> images = new ArrayList<>();
        imageMaterialRepository.findByThemeOrderByPriorityAsc(theme.get()).
                forEach(image -> images.add(image.toApiRepresentation()));
        return new ResponseEntity<>(images, HttpStatus.OK);
    }

    public ResponseEntity<List<ThemeStatus>> themeStatuses(@NotNull String token, @NotNull Long themeId) {
        Optional<CourseTheme> theme = themeRepository.findById(themeId);
        if (!theme.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<ThemeStatus> statuses = new ArrayList<>();
        themeStatusRepository.findByTheme(theme.get()).forEach(status -> statuses.add(status.toApiRepresentation()));
        return new ResponseEntity<>(statuses, HttpStatus.OK);
    }

    public ResponseEntity<List<CreativeTask>> themeTasks(@NotNull String token, @NotNull Long themeId) {
        Optional<CourseTheme> theme = themeRepository.findById(themeId);
        if (!theme.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<CreativeTask> tasks = new ArrayList<>();
        creativeTaskRepository.findByThemeOrderByPriorityAsc(theme.get()).
                forEach(task -> tasks.add(task.toApiRepresentation()));
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    public ResponseEntity<List<Test>> themeTests(@NotNull String token, @NotNull Long themeId) {
        Optional<CourseTheme> theme = themeRepository.findById(themeId);
        if (!theme.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<Test> tests = new ArrayList<>();
        testRepository.findByThemeOrderByPriorityAsc(theme.get()).
                forEach(test -> tests.add(test.toApiRepresentation()));
        return new ResponseEntity<>(tests, HttpStatus.OK);
    }

    public ResponseEntity<List<hmim.eteam.rest.backend.model.TextMaterial>> themeTexts(
            @NotNull String token, @NotNull Long themeId) {
        Optional<CourseTheme> theme = themeRepository.findById(themeId);
        if (!theme.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<hmim.eteam.rest.backend.model.TextMaterial> texts = new ArrayList<>();
        textMaterialRepository.findByThemeOrderByPriorityAsc(theme.get()).
                forEach(text -> texts.add(text.toApiRepresentation()));
        return new ResponseEntity<>(texts, HttpStatus.OK);
    }

    public ResponseEntity<List<hmim.eteam.rest.backend.model.VideoMaterial>> themeVideos(
            @NotNull String token, @NotNull Long themeId) {
        Optional<CourseTheme> theme = themeRepository.findById(themeId);
        if (!theme.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<hmim.eteam.rest.backend.model.VideoMaterial> videos = new ArrayList<>();
        videoMaterialRepository.findByThemeOrderByPriorityAsc(theme.get()).
                forEach(video -> videos.add(video.toApiRepresentation()));
        return new ResponseEntity<>(videos, HttpStatus.OK);
    }

    public ResponseEntity<Void> themePost(String token, @NotNull ThemeSave themeSave) {
        if (token == null) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        UserRole role = roleResolver.resolve(token, themeSave.getId());
        if (role == UserRole.Admin) {
            Optional<hmim.eteam.rest.backend.entity.course.Course> course =
                    courseRepository.findById(themeSave.getId());

            if (!course.isPresent()) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            themeRepository.save(new CourseTheme(
                    themeSave.getTheme().getPriority(),
                    course.get(),
                    themeSave.getTheme().getName()));

            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    public ResponseEntity<Void> taskPost(String token, @NotNull TaskSave taskSave) {
        if (token == null) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        Optional<CourseTheme> theme = themeRepository.findById(taskSave.getId());
        if (!theme.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        UserRole role = roleResolver.resolve(token, theme.get().getCourse().getId());
        if (role == UserRole.Admin) {
            creativeTaskRepository.save(new hmim.eteam.rest.backend.entity.task.CreativeTask(
                    taskSave.getTask().getPriority(),
                    theme.get(),
                    taskSave.getTask().getName(),
                    taskSave.getTask().getText()));

            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    public ResponseEntity<Void> testPost(String token, TestSave testSave) {
        Optional<CourseTheme> theme = themeRepository.findById(testSave.getId());
        if (!theme.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (token == null) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        UserRole role = roleResolver.resolve(token, theme.get().getCourse().getId());
        if (role == UserRole.Admin) {
            hmim.eteam.rest.backend.entity.test.Test test = testRepository.save(
                    new hmim.eteam.rest.backend.entity.test.Test(
                            testSave.getTest().getInfo().getPriority(),
                            theme.get(),
                            testSave.getTest().getInfo().getName()));

            for (TestQuestion question : testSave.getTest().getQuestions()) {
                testQuestionRepository.save(new hmim.eteam.rest.backend.entity.test.TestQuestion(
                        question.getPriority(),
                        test,
                        question.getText()
                ));
            }

            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
}
