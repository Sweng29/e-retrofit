package com.retrofit.app.controller.v1;

import com.retrofit.app.dto.UserDTO;
import com.retrofit.app.payload.request.LoginRequest;
import com.retrofit.app.payload.request.SignUpPayload;
import com.retrofit.app.payload.response.JwtAuthenticationResponse;
import com.retrofit.app.service.UserService;
import io.swagger.annotations.Api;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @PostMapping(value = "/user/authenticate")
    public ResponseEntity<JwtAuthenticationResponse> createAuthenticationToken(@RequestBody
            LoginRequest loginRequest){
        return ResponseEntity.ok(
                new JwtAuthenticationResponse(userService.authenticateUser(
                        loginRequest.getUsernameOrEmailOrMobileNo(),
                        loginRequest.getPassword())));
    }

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
