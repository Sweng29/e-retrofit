package com.retrofit.app.controller.v1;

import com.retrofit.app.dto.UserDTO;
import com.retrofit.app.payload.request.SignUpPayload;
import com.retrofit.app.service.UserService;
import io.swagger.annotations.Api;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/public/users")
@Api(tags = {"Public User controller"})
public class PublicUserController{

    @Autowired
    private UserService userService;

    @PostMapping
    public UserDTO signUp(@Valid @RequestBody SignUpPayload signUpPayload)
    {
        return userService.signUp(signUpPayload);
    }

    @GetMapping("/user/checkUserAvailability")
    public Boolean checkUsernameAvailability(
            @RequestParam(value = "usernameEmailOrMobileNo") String usernameEmailOrMobileNo) {
        return userService.existsByUsernameOrEmailOrMobileNumber(usernameEmailOrMobileNo);
    }

}
