package hmim.eteam.rest.backend.repository.user;

import hmim.eteam.rest.backend.entity.course.Course;
import hmim.eteam.rest.backend.entity.user.CourseRole;
import hmim.eteam.rest.backend.entity.user.SiteUser;
import hmim.eteam.rest.backend.entity.user.UserRole;
import hmim.eteam.rest.backend.repository.course.CourseRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CourseRoleRepositoryTests {
    @Autowired
    private SiteUserRepository siteUserRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CourseRoleRepository courseRoleRepository;

    public void clearDatabase() {
        siteUserRepository.deleteAll();
        courseRepository.deleteAll();
        courseRoleRepository.deleteAll();
    }

    @Test
    public void findBySiteUserAndCourseExists() {
        clearDatabase();

        SiteUser firstUser = new SiteUser("Name", "Login", "Password", false);
        siteUserRepository.save(firstUser);

        Course firstCourse = new Course("CourseName");
        courseRepository.save(firstCourse);

        CourseRole firstCourseRole = new CourseRole(firstUser, firstCourse, UserRole.Student);
        courseRoleRepository.save(firstCourseRole);

        Optional<CourseRole> courseRole = courseRoleRepository.findTopBySiteUserAndCourse(firstUser, firstCourse);

        Assert.assertNotNull(courseRole);
        Assert.assertTrue(courseRole.isPresent());
        Assert.assertEquals(courseRole.get(), firstCourseRole);
    }

    @Test
    public void findBySiteUserAndCourseNotExists() {
        clearDatabase();

        SiteUser firstUser = new SiteUser("Name", "Login", "Password", false);
        siteUserRepository.save(firstUser);

        SiteUser secondUser = new SiteUser("Name", "Login", "Password", false);
        siteUserRepository.save(secondUser);

        Course firstCourse = new Course("CourseName");
        courseRepository.save(firstCourse);

        CourseRole firstCourseRole = new CourseRole(firstUser, firstCourse, UserRole.Student);
        courseRoleRepository.save(firstCourseRole);

        Optional<CourseRole> courseRole = courseRoleRepository.findTopBySiteUserAndCourse(secondUser, firstCourse);

        Assert.assertNotNull(courseRole);
        Assert.assertFalse(courseRole.isPresent());
    }
}
