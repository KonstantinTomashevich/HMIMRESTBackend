package hmim.eteam.rest.backend.api;

import hmim.eteam.rest.backend.entity.course.Course;
import hmim.eteam.rest.backend.entity.user.AuthToken;
import hmim.eteam.rest.backend.entity.user.CourseRole;
import hmim.eteam.rest.backend.entity.user.SiteUser;
import hmim.eteam.rest.backend.entity.user.UserRole;
import hmim.eteam.rest.backend.repository.course.CourseRepository;
import hmim.eteam.rest.backend.repository.user.AuthTokenRepository;
import hmim.eteam.rest.backend.repository.user.CourseRoleRepository;
import hmim.eteam.rest.backend.repository.user.SiteUserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@DataJpaTest
public class RoleResolverTests {
    private static class Context {
        public AuthToken token;
        public RoleResolver resolver;
        public Course course;
    }

    @Autowired
    private SiteUserRepository siteUserRepository;

    @Autowired
    private AuthTokenRepository authTokenRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CourseRoleRepository courseRoleRepository;

    private void clearDatabase() {
        siteUserRepository.deleteAll();
        authTokenRepository.deleteAll();
        courseRepository.deleteAll();
        courseRoleRepository.deleteAll();
    }

    @Test
    public void noTokenGuest() {
        clearDatabase();
        Assert.assertEquals(new RoleResolver(authTokenRepository, courseRepository, courseRoleRepository).
                resolve("fake token", null), UserRole.Guest);
    }

    private Context setup(boolean superAdmin, UserRole role) {
        clearDatabase();
        Context context = new Context();

        context.resolver = new RoleResolver(authTokenRepository, courseRepository, courseRoleRepository);
        context.course = courseRepository.save(new Course(1, "course"));

        SiteUser user = new SiteUser("noname", "", "", superAdmin);
        user = siteUserRepository.save(user);

        context.token = authTokenRepository.save(
                new AuthToken("token", user, new Date(new Date().toInstant().toEpochMilli() + 10000)));

        if (role != null) {
            courseRoleRepository.save(new CourseRole(user, context.course, role));
        }

        return context;
    }

    @Test
    public void guestInACourse() {
        Context context = setup(false, UserRole.Guest);
        Assert.assertEquals(context.resolver.resolve(
                context.token.getId(), context.course.getId()), UserRole.Guest);
    }

    @Test
    public void studentInACourse() {
        Context context = setup(false, UserRole.Student);
        Assert.assertEquals(context.resolver.resolve(
                context.token.getId(), context.course.getId()), UserRole.Student);
    }

    @Test
    public void adminInACourse() {
        Context context = setup(false, UserRole.Admin);
        Assert.assertEquals(context.resolver.resolve(
                context.token.getId(), context.course.getId()), UserRole.Admin);
    }

    @Test
    public void superAdminWithoutRudimentaryRole() {
        Context context = setup(true, null);
        Assert.assertEquals(context.resolver.resolve(
                context.token.getId(), context.course.getId()), UserRole.Admin);
    }

    @Test
    public void superAdminWithRudimentaryGuestInACourse() {
        Context context = setup(true, UserRole.Guest);
        Assert.assertEquals(context.resolver.resolve(
                context.token.getId(), context.course.getId()), UserRole.Admin);
    }

    @Test
    public void superAdminWithRudimentaryStudentInACourse() {
        Context context = setup(true, UserRole.Student);
        Assert.assertEquals(context.resolver.resolve(
                context.token.getId(), context.course.getId()), UserRole.Admin);
    }

    @Test
    public void superAdminWithRudimentaryAdminInACourse() {
        Context context = setup(true, UserRole.Admin);
        Assert.assertEquals(context.resolver.resolve(
                context.token.getId(), context.course.getId()), UserRole.Admin);
    }
}
