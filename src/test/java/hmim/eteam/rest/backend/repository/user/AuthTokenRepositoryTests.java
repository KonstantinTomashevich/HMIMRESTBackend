package hmim.eteam.rest.backend.repository.user;

import hmim.eteam.rest.backend.entity.course.Course;
import hmim.eteam.rest.backend.entity.course.CourseTheme;
import hmim.eteam.rest.backend.entity.user.AuthToken;
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
public class AuthTokenRepositoryTests {

    @Autowired
    private AuthTokenRepository authTokenRepository;

    @Autowired
    private SiteUserRepository siteUserRepository;

    public void clearDatabase(){
        authTokenRepository.deleteAll();
        siteUserRepository.deleteAll();
    }

    @Test
    public void resolveTokenExists(){
        clearDatabase();
        SiteUser firstUser = new SiteUser("Name", "Login", "Password", false);
        siteUserRepository.save(firstUser);
        AuthToken firstAuthToken = new AuthToken("id",firstUser,new Date(new Date().getTime()+1000000));
        authTokenRepository.save(firstAuthToken);

        Optional<AuthToken> authToken = authTokenRepository.resolveToken("id");
        Assert.assertTrue(authToken.isPresent());

        //different objects, this causes test failture
        Assert.assertEquals(authToken.get(),firstAuthToken);

    }

    @Test
    public void resolveTokenExistsButExpired(){
        clearDatabase();
        SiteUser firstUser = new SiteUser("Name", "Login", "Password", false);
        siteUserRepository.save(firstUser);
        AuthToken firstAuthToken = new AuthToken("id",firstUser,new Date());
        authTokenRepository.save(firstAuthToken);

        Optional<AuthToken> authToken = authTokenRepository.resolveToken("id");
        Assert.assertFalse(authToken.isPresent());

    }

    @Test
    public void resolveTokenNotExists(){
        clearDatabase();
        SiteUser firstUser = new SiteUser("Name", "Login", "Password", false);
        siteUserRepository.save(firstUser);
        AuthToken firstAuthToken = new AuthToken("id",firstUser,new Date(new Date().getTime()+10000));
        authTokenRepository.save(firstAuthToken);

        Optional<AuthToken> authToken = authTokenRepository.resolveToken("id1");
        Assert.assertFalse(authToken.isPresent());
    }
}