package hmim.eteam.rest.backend.controller;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import hmim.eteam.rest.backend.common.AuthUtils;
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

import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
public class AuthController {
    private static final String folder = "/auth/";

    private SiteUserRepository siteUserRepository;
    private AuthTokenRepository authTokenRepository;

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

        return gson.toJson(AuthUtils.generateToken(user, authTokenRepository));
    }

    private void applyMD5(LoginData loginData) throws NoSuchAlgorithmException {
        loginData.login = AuthUtils.md5(loginData.login);
        loginData.password = AuthUtils.md5(loginData.password);
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
            return gson.toJson(AuthUtils.generateToken(user.get(), authTokenRepository));
        } else {
            return gson.toJson(new ResultCode(ResultCode.Codes.LOGIN_FAILED.ordinal()));
        }
    }
}
