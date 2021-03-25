package com.retrofit.app.controller.v1;

import com.retrofit.app.dto.UserDTO;
import com.retrofit.app.payload.SignUpPayload;
import com.retrofit.app.service.UserService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/public/users")
public class PublicUserController{

    @Autowired
    private UserService userService;

    @PostMapping
    public UserDTO signUp(@Valid @RequestBody SignUpPayload signUpPayload)
    {
        return userService.signUp(signUpPayload);
    }

}
