package hmim.eteam.rest.backend.controller;

import com.google.gson.Gson;
import hmim.eteam.rest.backend.representation.input.RegistrationData;
import hmim.eteam.rest.backend.representation.output.ResultCode;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    private static final String folder = "/auth/";

    @PostMapping(folder + "register")
    @ResponseBody
    public String register(@RequestBody String dataJson)
    {
        return new Gson().toJson(new ResultCode(ResultCode.Codes.INCORRECT_INPUT_FORMAT.ordinal()));
    }
}
