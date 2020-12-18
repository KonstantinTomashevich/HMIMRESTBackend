package hmim.eteam.rest.backend.controller;

import hmim.eteam.rest.backend.api.IRoleResolver;
import hmim.eteam.rest.backend.entity.user.UserRole;
import hmim.eteam.rest.backend.model.Course;
import hmim.eteam.rest.backend.model.CourseRole;
import hmim.eteam.rest.backend.model.Theme;
import hmim.eteam.rest.backend.repository.course.CourseRepository;
import hmim.eteam.rest.backend.repository.course.ThemeStatusRepository;
import hmim.eteam.rest.backend.repository.user.CourseRoleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CourseController {
    private final IRoleResolver roleResolver;
    private final CourseRepository courseRepository;
    private final CourseRoleRepository courseRoleRepository;

    // Adhook for ::courses.
    private final ThemeStatusRepository themeStatusRepository;

    public CourseController(IRoleResolver roleResolver, CourseRepository courseRepository,
                            CourseRoleRepository courseRoleRepository,
                            ThemeStatusRepository themeStatusRepository) {
        this.roleResolver = roleResolver;
        this.courseRepository = courseRepository;
        this.courseRoleRepository = courseRoleRepository;
        this.themeStatusRepository = themeStatusRepository;
    }

    public ResponseEntity<List<CourseRole>> courseRoles(String token, @NotNull Long courseId) {
        if (token == null) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        Optional<hmim.eteam.rest.backend.entity.course.Course> course = courseRepository.findById(courseId);
        if (!course.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        UserRole role = roleResolver.resolve(token, courseId);
        if (role == UserRole.Admin) {
            List<hmim.eteam.rest.backend.entity.user.CourseRole> allRoles =
                    courseRoleRepository.findByCourse(course.get());

            List<CourseRole> allRolesRepresentation = new ArrayList<>();
            allRoles.forEach(foundRole -> allRolesRepresentation.add(foundRole.toApiRepresentation()));
            return new ResponseEntity<>(allRolesRepresentation, HttpStatus.OK);

        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    public ResponseEntity<List<Course>> courses(String token) {
        List<Course> courses = new ArrayList<>();
        courseRepository.findAll().forEach(sourceCourse ->
        {
            // Adhook, but ok for now.
            Course course = new Course();
            course.id(sourceCourse.getId()).
                    name(sourceCourse.getName()).
                    priority(sourceCourse.getPriority())
                    .participantsQuantity(
                            courseRoleRepository.countDistinctByCourseAndRole(sourceCourse, UserRole.Student) +
                                    courseRoleRepository.countDistinctByCourseAndRole(sourceCourse, UserRole.Admin));

            sourceCourse.getCourseThemes().forEach(courseTheme -> course.addThemesItem(new Theme().
                    id(courseTheme.getId()).
                    name(courseTheme.getName()).
                    priority(courseTheme.getPriority()))
                    .participantsQuantity(themeStatusRepository.countDistinctByThemeAndSeenTrue(courseTheme)));

            sourceCourse.getForumThemes().forEach(
                    forumTheme -> course.addForumThemesItem(forumTheme.toApiRepresentation()));
            courses.add(course);
        });

        return new ResponseEntity<>(courses, HttpStatus.OK);
    }
}
