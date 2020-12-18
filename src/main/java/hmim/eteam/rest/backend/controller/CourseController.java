package hmim.eteam.rest.backend.controller;

import hmim.eteam.rest.backend.api.IRoleResolver;
import hmim.eteam.rest.backend.entity.user.SiteUser;
import hmim.eteam.rest.backend.entity.user.UserRole;
import hmim.eteam.rest.backend.model.AssignRole;
import hmim.eteam.rest.backend.model.Course;
import hmim.eteam.rest.backend.model.CourseRole;
import hmim.eteam.rest.backend.model.Theme;
import hmim.eteam.rest.backend.repository.course.CourseRepository;
import hmim.eteam.rest.backend.repository.course.ThemeStatusRepository;
import hmim.eteam.rest.backend.repository.user.CourseRoleRepository;
import hmim.eteam.rest.backend.repository.user.SiteUserRepository;
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
    private final SiteUserRepository siteUserRepository;

    public CourseController(IRoleResolver roleResolver, CourseRepository courseRepository,
                            CourseRoleRepository courseRoleRepository,
                            ThemeStatusRepository themeStatusRepository,
                            SiteUserRepository siteUserRepository) {
        this.roleResolver = roleResolver;
        this.courseRepository = courseRepository;
        this.courseRoleRepository = courseRoleRepository;
        this.themeStatusRepository = themeStatusRepository;
        this.siteUserRepository = siteUserRepository;
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

    public ResponseEntity<Void> coursePost(String token, @NotNull Course course) {
        if (token == null) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        UserRole role = roleResolver.resolve(token, null);
        if (role == UserRole.Admin) {

            courseRepository.save(new hmim.eteam.rest.backend.entity.course.Course(
                    course.getPriority(),
                    course.getName()));
            return new ResponseEntity<>(HttpStatus.OK);

        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    public ResponseEntity<Void> assignRolePost(String token, @NotNull AssignRole data) {
        if (token == null) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        UserRole role = roleResolver.resolve(token, data.getCourseId());
        if (role == UserRole.Admin) {
            Optional<hmim.eteam.rest.backend.entity.course.Course> course =
                    courseRepository.findById(data.getCourseId());

            if (!course.isPresent()) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            Optional<SiteUser> siteUser = siteUserRepository.findById(data.getCourseId());
            if (!siteUser.isPresent()) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            Optional<hmim.eteam.rest.backend.entity.user.CourseRole> courseRole =
                    courseRoleRepository.findTopBySiteUserAndCourse(siteUser.get(), course.get());

            if (!courseRole.isPresent()) {
                courseRoleRepository.save(new hmim.eteam.rest.backend.entity.user.CourseRole(
                        siteUser.get(),
                        course.get(),
                        UserRole.valueOf(data.getRole())));

            } else {
                courseRole.get().setRole(UserRole.valueOf(data.getRole()));
                courseRoleRepository.save(courseRole.get());
            }

            return new ResponseEntity<>(HttpStatus.OK);

        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
}
