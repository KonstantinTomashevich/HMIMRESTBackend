package hmim.eteam.rest.backend.controller;

import com.google.gson.Gson;
import hmim.eteam.rest.backend.entity.course.Course;
import hmim.eteam.rest.backend.entity.forum.ForumMessage;
import hmim.eteam.rest.backend.entity.forum.ForumTheme;
import hmim.eteam.rest.backend.repository.course.CourseRepository;
import hmim.eteam.rest.backend.repository.forum.MessageRepository;
import hmim.eteam.rest.backend.repository.forum.ThemeRepository;
import hmim.eteam.rest.backend.representation.output.MessageInfo;
import hmim.eteam.rest.backend.representation.output.ResultCode;
import hmim.eteam.rest.backend.representation.output.ThemeInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class ForumController {
    private static final String folder = "/forum/";

    private ThemeRepository themeRepository;
    private MessageRepository messageRepository;
    private CourseRepository courseRepository;

    public ForumController(ThemeRepository themeRepository, MessageRepository messageRepository, CourseRepository courseRepository) {
        this.themeRepository = themeRepository;
        this.messageRepository = messageRepository;
        this.courseRepository = courseRepository;
    }

    @GetMapping(folder + "messages/{themeId}")
    @ResponseBody
    public String getMessages(@PathVariable long themeId) {
        Gson gson = new Gson();
        Optional<ForumTheme> theme = themeRepository.findById(themeId);

        if (!theme.isPresent()) {
            return gson.toJson(new ResultCode(ResultCode.Codes.NOT_EXISTING_FORUM_THEME.ordinal()));
        }

        List<ForumMessage> messages = messageRepository.findByThemeOrderByDateAsc(theme.get());
        return gson.toJson(messages.stream().map(MessageInfo::new).toArray());
    }

    @GetMapping(folder + "themes/{courseId}")
    @ResponseBody
    public String getThemes(@PathVariable long courseId) {
        Gson gson = new Gson();
        Optional<Course> course = courseRepository.findById(courseId);

        if (!course.isPresent()) {
            return gson.toJson(new ResultCode(ResultCode.Codes.NOT_EXISTING_COURSE.ordinal()));
        }

        List<ForumTheme> themes = themeRepository.findByCourseOrderByLastUpdateDateDesc(course.get());
        return gson.toJson(themes.stream().map(ThemeInfo::new).toArray());
    }
}
