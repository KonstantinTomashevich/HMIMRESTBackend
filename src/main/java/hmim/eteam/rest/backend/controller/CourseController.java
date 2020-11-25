package hmim.eteam.rest.backend.controller;

import hmim.eteam.rest.backend.api.IRoleResolver;
import hmim.eteam.rest.backend.entity.user.UserRole;
import hmim.eteam.rest.backend.model.Course;
import hmim.eteam.rest.backend.model.CourseRole;
import hmim.eteam.rest.backend.repository.course.CourseRepository;
import hmim.eteam.rest.backend.repository.user.CourseRoleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CourseController {
    private final IRoleResolver roleResolver;
    private final CourseRepository courseRepository;
    private final CourseRoleRepository courseRoleRepository;

    public CourseController(IRoleResolver roleResolver, CourseRepository courseRepository,
                            CourseRoleRepository courseRoleRepository) {
        this.roleResolver = roleResolver;
        this.courseRepository = courseRepository;
        this.courseRoleRepository = courseRoleRepository;
    }

    public ResponseEntity<CourseRole> courseRoles(String token, @NotNull String courseIdString) {
        if (token == null) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        long courseId;
        try {
            courseId = Long.parseLong(courseIdString);
        } catch (Exception exception) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, exception.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Optional<hmim.eteam.rest.backend.entity.course.Course> course = courseRepository.findById(courseId);
        if (!course.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        UserRole role = roleResolver.resolve(token, courseId);
        if (role == UserRole.Admin) {
            List<hmim.eteam.rest.backend.entity.user.CourseRole> allRoles =
                    courseRoleRepository.findByCourse(course.get());

            // TODO: Temporary stub because of incorrect API specification.
            if (allRoles.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(allRoles.get(0).toApiRepresentation(), HttpStatus.OK);
            }

        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    public ResponseEntity<List<Course>> courses(String token) {
        List<Course> courses = new ArrayList<>();
        courseRepository.findAll().forEach(course -> courses.add(course.toApiRepresentation()));
        return new ResponseEntity<>(courses, HttpStatus.OK);
    }
}
