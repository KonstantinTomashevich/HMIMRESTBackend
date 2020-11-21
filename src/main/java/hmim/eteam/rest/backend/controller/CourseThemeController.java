package hmim.eteam.rest.backend.controller;

import com.google.gson.Gson;
import hmim.eteam.rest.backend.entity.course.CourseTheme;
import hmim.eteam.rest.backend.repository.course.CourseThemeRepository;
import hmim.eteam.rest.backend.representation.output.ResultCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class CourseThemeController {
    private static final String folder = "/theme/";

    private CourseThemeRepository courseThemeRepository;

    @GetMapping(folder + "{themeId}/materials/all")
    @ResponseBody
    public String getAllEntries(@PathVariable long themeId) {
        Gson gson = new Gson();
        Optional<CourseTheme> theme = courseThemeRepository.findById(themeId);

        if (!theme.isPresent()) {
            return gson.toJson(new ResultCode(ResultCode.Codes.NOT_EXISTING_USER.ordinal()));
        }

        // TODO: Implement material search and merging.
        //List<CourseThemeEntry> entries = anyCourseThemeEntryRepository.findByThemeOrderByPriorityAsc(theme.get());
        //return gson.toJson(entries.stream().map(CourseThemeEntryBriefInfo::new).toArray());
        return gson.toJson(new ResultCode(ResultCode.Codes.NOT_EXISTING_USER.ordinal()));
    }
}
