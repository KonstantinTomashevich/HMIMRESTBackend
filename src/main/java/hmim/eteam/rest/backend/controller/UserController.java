package hmim.eteam.rest.backend.controller;

import com.google.gson.Gson;
import hmim.eteam.rest.backend.entity.user.SiteUser;
import hmim.eteam.rest.backend.repository.user.SiteUserRepository;
import hmim.eteam.rest.backend.representation.output.ResultCode;
import hmim.eteam.rest.backend.representation.output.UserInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class UserController {
    private static final String folder = "/user/";

    private SiteUserRepository siteUserRepository;

    public UserController(SiteUserRepository siteUserRepository) {
        this.siteUserRepository = siteUserRepository;
    }

    @GetMapping(folder + "info/{userId}")
    @ResponseBody
    public String getUserInfo(@PathVariable long userId) {
        Gson gson = new Gson();
        Optional<SiteUser> user = siteUserRepository.findById(userId);

        if (user.isPresent()) {
            return gson.toJson(new UserInfo(user.get()));
        } else {
            return gson.toJson(new ResultCode(ResultCode.Codes.NOT_EXISTING_USER.ordinal()));
        }
    }
}
