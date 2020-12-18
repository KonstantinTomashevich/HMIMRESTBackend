package hmim.eteam.rest.backend.controller;

import hmim.eteam.rest.backend.api.IRoleResolver;
import hmim.eteam.rest.backend.entity.course.CourseTheme;
import hmim.eteam.rest.backend.model.CreativeTask;
import hmim.eteam.rest.backend.model.ImageMaterial;
import hmim.eteam.rest.backend.model.Test;
import hmim.eteam.rest.backend.model.ThemeStatus;
import hmim.eteam.rest.backend.repository.course.CourseThemeRepository;
import hmim.eteam.rest.backend.repository.course.ThemeStatusRepository;
import hmim.eteam.rest.backend.repository.learning.ImageMaterialRepository;
import hmim.eteam.rest.backend.repository.learning.TextMaterialRepository;
import hmim.eteam.rest.backend.repository.learning.VideoMaterialRepository;
import hmim.eteam.rest.backend.repository.task.CreativeTaskRepository;
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

    public ThemeController(IRoleResolver roleResolver,
                           CourseThemeRepository themeRepository,
                           ImageMaterialRepository imageMaterialRepository,
                           VideoMaterialRepository videoMaterialRepository,
                           TestRepository testRepository,
                           TextMaterialRepository textMaterialRepository,
                           CreativeTaskRepository creativeTaskRepository,
                           ThemeStatusRepository themeStatusRepository) {
        this.roleResolver = roleResolver;
        this.themeRepository = themeRepository;
        this.imageMaterialRepository = imageMaterialRepository;
        this.videoMaterialRepository = videoMaterialRepository;
        this.textMaterialRepository = textMaterialRepository;
        this.testRepository = testRepository;
        this.creativeTaskRepository = creativeTaskRepository;
        this.themeStatusRepository = themeStatusRepository;
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
}
