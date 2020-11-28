package hmim.eteam.rest.backend.controller;

import hmim.eteam.rest.backend.model.AuthenticationToken;
import hmim.eteam.rest.backend.model.UserRegistrationInfo;
import hmim.eteam.rest.backend.repository.user.AuthTokenRepository;
import hmim.eteam.rest.backend.repository.user.SiteUserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

// TODO: Check tests guide. Maybe not JPA?
// TODO: Temporary test, better implementation later.
@RunWith(SpringRunner.class)
@DataJpaTest
public class AuthControllerTests {
    @Autowired
    private SiteUserRepository siteUserRepository;

    @Autowired
    private AuthTokenRepository authTokenRepository;

    private void clearDatabase(){
        siteUserRepository.deleteAll();
        authTokenRepository.deleteAll();
    }

    @Test
    public void justRegisterUser()
    {
        clearDatabase();
        AuthController authController = new AuthController(siteUserRepository, authTokenRepository);
        String testName = "Hello, world!";

        ResponseEntity<AuthenticationToken> response = authController.register(
                new UserRegistrationInfo().login("testlogin").password("testpassword").name(testName));

        Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
        Assert.assertNotNull(response.getBody());
    }
}
