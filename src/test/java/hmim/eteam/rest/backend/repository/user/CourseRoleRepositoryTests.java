package hmim.eteam.rest.backend.repository.user;

import hmim.eteam.rest.backend.entity.course.Course;
import hmim.eteam.rest.backend.entity.course.CourseTheme;
import hmim.eteam.rest.backend.entity.user.CourseRole;
import hmim.eteam.rest.backend.entity.user.UserRole;
import hmim.eteam.rest.backend.repository.test.TestUserAnswerRepository;
import hmim.eteam.rest.backend.entity.task.CreativeTask;
import hmim.eteam.rest.backend.entity.task.CreativeTaskAnswer;
import hmim.eteam.rest.backend.entity.test.*;
import hmim.eteam.rest.backend.entity.user.SiteUser;
import hmim.eteam.rest.backend.repository.course.CourseRepository;
import hmim.eteam.rest.backend.repository.course.CourseThemeRepository;
import hmim.eteam.rest.backend.repository.test.*;
import hmim.eteam.rest.backend.repository.user.SiteUserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

    public void clearDatabase(){
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

        CourseRole firstCourseRole = new CourseRole(firstUser,firstCourse, UserRole.Student);
        courseRoleRepository.save(firstCourseRole);

        List<CourseRole> courseRoles = courseRoleRepository.findBySiteUserAndCourse(firstUser,firstCourse);

        Assert.assertNotNull(courseRoles);
        Assert.assertEquals(courseRoles.size(),1);
        Assert.assertEquals(courseRoles.get(0),firstCourseRole);
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

        CourseRole firstCourseRole = new CourseRole(firstUser,firstCourse, UserRole.Student);
        courseRoleRepository.save(firstCourseRole);

        List<CourseRole> courseRoles = courseRoleRepository.findBySiteUserAndCourse(secondUser,firstCourse);

        Assert.assertNotNull(courseRoles);
        Assert.assertTrue(courseRoles.isEmpty());
    }

    @Test
    public void findBySiteUserAndCourse() {
        clearDatabase();

        SiteUser firstUser = new SiteUser("Name", "Login", "Password", false);
        siteUserRepository.save(firstUser);

        Course firstCourse = new Course("CourseName");
        courseRepository.save(firstCourse);

        List<CourseRole> courseRoles = new ArrayList<CourseRole>();
        for(int i =0;i<10;i++) {
            courseRoles.add(new CourseRole(firstUser,firstCourse, UserRole.Student));
            courseRoleRepository.save(courseRoles.get(courseRoles.size()-1));
        }

        List<CourseRole> foundedCourseRoles = courseRoleRepository.findBySiteUserAndCourse(firstUser,firstCourse);

        Assert.assertNotNull(foundedCourseRoles);
        Assert.assertEquals(foundedCourseRoles.size(),courseRoles.size());
        Assert.assertTrue(foundedCourseRoles.containsAll(courseRoles));
        Assert.assertTrue(courseRoles.containsAll(foundedCourseRoles));

    }
}
