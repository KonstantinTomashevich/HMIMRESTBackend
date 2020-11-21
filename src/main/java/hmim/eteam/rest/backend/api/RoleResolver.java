package hmim.eteam.rest.backend.api;

import hmim.eteam.rest.backend.entity.course.Course;
import hmim.eteam.rest.backend.entity.user.AuthToken;
import hmim.eteam.rest.backend.entity.user.CourseRole;
import hmim.eteam.rest.backend.entity.user.UserRole;
import hmim.eteam.rest.backend.repository.course.CourseRepository;
import hmim.eteam.rest.backend.repository.user.AuthTokenRepository;
import hmim.eteam.rest.backend.repository.user.CourseRoleRepository;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;
import java.util.Optional;

public class RoleResolver {
    private final AuthTokenRepository authTokenRepository;
    private final CourseRepository courseRepository;
    private final CourseRoleRepository courseRoleRepository;

    public RoleResolver(AuthTokenRepository authTokenRepository, CourseRepository courseRepository,
                        CourseRoleRepository courseRoleRepository) {
        this.authTokenRepository = authTokenRepository;
        this.courseRepository = courseRepository;
        this.courseRoleRepository = courseRoleRepository;
    }

    public UserRole resolve(@NotNull String authTokenId, @Nullable Long courseId) {
        Optional<AuthToken> authToken = authTokenRepository.resolveToken(authTokenId);
        if (!authToken.isPresent()) {
            return UserRole.Guest;

        } else if (authToken.get().getUser().isSuperAdmin()) {
            return UserRole.Admin;

        } else if (courseId == null) {
            return UserRole.Guest;

        } else {
            Optional<Course> course = courseRepository.findById(courseId);
            if (!course.isPresent()) {
                return UserRole.Guest;
            }

            Optional<CourseRole> role =
                    courseRoleRepository.findTopBySiteUserAndCourse(authToken.get().getUser(), course.get());

            if (!role.isPresent()) {
                return UserRole.Guest;
            } else {
                return role.get().getRole();
            }
        }
    }
}