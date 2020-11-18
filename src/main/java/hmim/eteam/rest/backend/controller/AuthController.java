package hmim.eteam.rest.backend.controller;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import hmim.eteam.rest.backend.entity.user.AuthToken;
import hmim.eteam.rest.backend.entity.user.SiteUser;
import hmim.eteam.rest.backend.repository.user.AuthTokenRepository;
import hmim.eteam.rest.backend.repository.user.SiteUserRepository;
import hmim.eteam.rest.backend.representation.input.LoginData;
import hmim.eteam.rest.backend.representation.input.RegistrationData;
import hmim.eteam.rest.backend.representation.output.ResultCode;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Optional;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
public class AuthController {
    private static final String folder = "/auth/";

    private SiteUserRepository siteUserRepository;
    private AuthTokenRepository authTokenRepository;

    public AuthController(SiteUserRepository siteUserRepository, AuthTokenRepository authTokenRepository) {
        this.siteUserRepository = siteUserRepository;
        this.authTokenRepository = authTokenRepository;
    }

    @PostMapping(folder + "register")
    @ResponseBody
    public String register(@RequestBody String dataJson) {
        Gson gson = new Gson();
        RegistrationData registrationData;

        try {
            registrationData = gson.fromJson(dataJson, RegistrationData.class);
        } catch (JsonIOException exception) {
            Logger.getLogger(getClass().getName()).log(Level.WARNING, exception.getMessage());
            return gson.toJson(new ResultCode(ResultCode.Codes.INCORRECT_INPUT_FORMAT.ordinal()));
        }

        try {
            applyMD5(registrationData);
        } catch (NoSuchAlgorithmException exception) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, exception.getMessage());
            return gson.toJson(new ResultCode(ResultCode.Codes.INTERNAL_ERROR.ordinal()));
        }

        if (siteUserRepository.existsByLoginMD5(registrationData.login)) {
            return gson.toJson(new ResultCode(ResultCode.Codes.LOGIN_TAKEN.ordinal()));
        }

        SiteUser user = siteUserRepository.save(new SiteUser(registrationData.name,
                registrationData.login, registrationData.password, false));

        return gson.toJson(generateToken(user));
    }

    public String md5(String input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(input.getBytes());
        return DatatypeConverter.printHexBinary(md.digest()).toUpperCase();
    }

    private void applyMD5(LoginData loginData) throws NoSuchAlgorithmException {
        loginData.login = md5(loginData.login);
        loginData.password = md5(loginData.password);
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

    @PostMapping(folder + "login")
    @ResponseBody
    public String login(@RequestBody String dataJson) {
        Gson gson = new Gson();
        LoginData loginData;

        try {
            loginData = gson.fromJson(dataJson, LoginData.class);
        } catch (JsonIOException exception) {
            Logger.getLogger(getClass().getName()).log(Level.WARNING, exception.getMessage());
            return gson.toJson(new ResultCode(ResultCode.Codes.INCORRECT_INPUT_FORMAT.ordinal()));
        }

        try {
            applyMD5(loginData);
        } catch (NoSuchAlgorithmException exception) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, exception.getMessage());
            return gson.toJson(new ResultCode(ResultCode.Codes.INTERNAL_ERROR.ordinal()));
        }

        Optional<SiteUser> user = siteUserRepository.findFirstByLoginMD5AndPasswordMD5(
                loginData.login, loginData.password);

        if (user.isPresent()) {
            return gson.toJson(generateToken(user.get()));
        } else {
            return gson.toJson(new ResultCode(ResultCode.Codes.LOGIN_FAILED.ordinal()));
        }
    }
}
