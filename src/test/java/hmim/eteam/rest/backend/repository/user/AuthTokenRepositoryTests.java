package hmim.eteam.rest.backend.repository.user;

import hmim.eteam.rest.backend.entity.user.AuthToken;
import hmim.eteam.rest.backend.entity.user.SiteUser;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.Optional;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AuthTokenRepositoryTests {

    @Autowired
    private AuthTokenRepository authTokenRepository;

    @Autowired
    private SiteUserRepository siteUserRepository;

    public void clearDatabase() {
        authTokenRepository.deleteAll();
        siteUserRepository.deleteAll();
    }

    @Test
    public void resolveTokenExists() {
        clearDatabase();
        SiteUser firstUser = new SiteUser("Name", "Login", "Password", false);
        siteUserRepository.save(firstUser);
        AuthToken firstAuthToken = new AuthToken("id", firstUser, new Date(new Date().getTime() + 1000000));
        authTokenRepository.save(firstAuthToken);

        Optional<AuthToken> authToken = authTokenRepository.resolveToken("id");
        Assert.assertTrue(authToken.isPresent());
        Assert.assertEquals(firstAuthToken, authToken.get());
    }

    @Test
    public void resolveTokenExistsButExpired() {
        clearDatabase();
        SiteUser firstUser = new SiteUser("Name", "Login", "Password", false);
        siteUserRepository.save(firstUser);
        AuthToken firstAuthToken = new AuthToken("id", firstUser, new Date(0));
        authTokenRepository.save(firstAuthToken);

        Optional<AuthToken> authToken = authTokenRepository.resolveToken("id");
        Assert.assertFalse(authToken.isPresent());
    }

    @Test
    public void resolveTokenNotExists() {
        clearDatabase();
        SiteUser firstUser = new SiteUser("Name", "Login", "Password", false);
        siteUserRepository.save(firstUser);
        AuthToken firstAuthToken = new AuthToken("id", firstUser, new Date(new Date().getTime() + 10000));
        authTokenRepository.save(firstAuthToken);

        Optional<AuthToken> authToken = authTokenRepository.resolveToken("id1");
        Assert.assertFalse(authToken.isPresent());
    }
}
