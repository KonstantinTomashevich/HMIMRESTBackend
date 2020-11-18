package hmim.eteam.rest.backend.repository.user;
import hmim.eteam.rest.backend.entity.course.Course;
import hmim.eteam.rest.backend.entity.course.CourseTheme;
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
public class SiteUserRepositoryTests {
    @Autowired
    private SiteUserRepository siteUserRepository;

    public void clearDatabase(){
        siteUserRepository.deleteAll();
    }

    @Test
    public void findByLoginMD5AndPasswordMD5Exists() {
        clearDatabase();

        SiteUser firstUser = new SiteUser("Name", "Login", "Password", false);
        siteUserRepository.save(firstUser);

        Optional<SiteUser> siteUser = siteUserRepository.findFirstByLoginMD5AndPasswordMD5("Login","Password");
        Assert.assertTrue(siteUser.isPresent());
        Assert.assertEquals(firstUser,siteUser.get());
    }

    @Test
    public void findByLoginMD5AndPasswordMD5NotExists() {
        clearDatabase();

        SiteUser firstUser = new SiteUser("Name", "Login", "Password", false);
        siteUserRepository.save(firstUser);

        Optional<SiteUser> siteUser = siteUserRepository.findFirstByLoginMD5AndPasswordMD5("Login111","Password");
        Assert.assertFalse(siteUser.isPresent());
    }

    @Test
    public void findFirstByLoginMD5AndPasswordMD5() {
        clearDatabase();

        SiteUser firstUser = new SiteUser("Name", "Login", "Password", false);
        siteUserRepository.save(firstUser);

        SiteUser secondUser = new SiteUser("Name", "Login", "Password", false);
        siteUserRepository.save(secondUser);

        Optional<SiteUser> siteUser = siteUserRepository.findFirstByLoginMD5AndPasswordMD5("Login","Password");
        Assert.assertTrue(siteUser.isPresent());
        Assert.assertEquals(siteUser.get(),firstUser);
    }

    @Test
    public void existsByLoginMD5() {
        clearDatabase();

        SiteUser firstUser = new SiteUser("Name", "Login", "Password", false);
        siteUserRepository.save(firstUser);

        boolean exists = siteUserRepository.existsByLoginMD5("Login");
        Assert.assertTrue(exists);
    }

    @Test
    public void notExistsByLoginMD5() {
        clearDatabase();

        SiteUser firstUser = new SiteUser("Name", "Login", "Password", false);
        siteUserRepository.save(firstUser);

        boolean exists = siteUserRepository.existsByLoginMD5("Login111");
        Assert.assertFalse(exists);
    }
}
