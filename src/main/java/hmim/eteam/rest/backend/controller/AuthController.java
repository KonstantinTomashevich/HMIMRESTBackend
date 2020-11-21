package hmim.eteam.rest.backend.controller;

import hmim.eteam.rest.backend.entity.user.AuthToken;
import hmim.eteam.rest.backend.entity.user.SiteUser;
import hmim.eteam.rest.backend.model.AuthenticationToken;
import hmim.eteam.rest.backend.model.UserLoginInfo;
import hmim.eteam.rest.backend.model.UserRegistrationInfo;
import hmim.eteam.rest.backend.repository.user.AuthTokenRepository;
import hmim.eteam.rest.backend.repository.user.SiteUserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Optional;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AuthController {
    private final SiteUserRepository siteUserRepository;
    private final AuthTokenRepository authTokenRepository;

    public AuthController(SiteUserRepository siteUserRepository, AuthTokenRepository authTokenRepository) {
        this.siteUserRepository = siteUserRepository;
        this.authTokenRepository = authTokenRepository;
    }

    public ResponseEntity<AuthenticationToken> register(UserRegistrationInfo registration) {
        try {
            registration.login(md5(registration.getLogin())).password(md5(registration.getPassword()));
        } catch (NoSuchAlgorithmException exception) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, exception.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (siteUserRepository.existsByLoginMD5(registration.getLogin())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        SiteUser user = siteUserRepository.save(new SiteUser(registration.getName(),
                registration.getLogin(), registration.getPassword(), false));

        return new ResponseEntity<>(new AuthenticationToken().value(generateToken(user).getId()), HttpStatus.OK);
    }

    public String md5(String input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(input.getBytes());
        return DatatypeConverter.printHexBinary(md.digest()).toUpperCase();
    }

    public AuthToken generateToken(SiteUser siteUser) {
        // TODO: Cleanup expired tokens?
        Random random = new Random();
        String id;
        int triesLeft = Integer.MAX_VALUE;

        do {
            id = Integer.toHexString(random.nextInt());
            --triesLeft;
        } while (authTokenRepository.existsById(id) || triesLeft == 0);

        return authTokenRepository.save(new AuthToken(id, siteUser, new Date()));
    }

    public ResponseEntity<AuthenticationToken> login(UserLoginInfo input) {
        try {
            input.login(md5(input.getLogin())).password(md5(input.getPassword()));
        } catch (NoSuchAlgorithmException exception) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, exception.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        Optional<SiteUser> user = siteUserRepository.findFirstByLoginMD5AndPasswordMD5(
                input.getLogin(), input.getPassword());

        return user.map(siteUser -> new ResponseEntity<>(
                new AuthenticationToken().value(generateToken(siteUser).getId()), HttpStatus.OK)).orElseGet(
                () -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }
}
