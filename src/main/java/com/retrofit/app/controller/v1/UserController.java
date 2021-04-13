package com.retrofit.app.controller.v1;

import com.retrofit.app.controller.BaseController;
import com.retrofit.app.dto.UserDTO;
import com.retrofit.app.filters.UserFilterAttributes;
import com.retrofit.app.payload.request.EditUserPayload;
import com.retrofit.app.payload.request.UserCreationPayload;
import com.retrofit.app.service.UserService;
import io.swagger.annotations.Api;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/api/users")
@Api(
        tags = {"User controller"},
        value = "Operations pertaining to users")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO getUserById(
            @Valid @Min(value = 1,message = "User id can not be less then 1")
            @PathVariable Long userId)
    {
        return userService.findUserById(userId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Secured({ROLE_ADMIN})
    public List<UserDTO> findAllUsers(@RequestBody UserFilterAttributes userFilterAttributes)
    {
        return userService.findAllUsers(userFilterAttributes);
    }

    @GetMapping("/user/current-user")
    @Secured({ROLE_ADMIN, ROLE_USER})
    public UserDTO getCurrentUserDetails() {
        return userService.findCurrentUserDetails();
    }

    @PutMapping("/user")
    @Secured({ROLE_ADMIN, ROLE_USER})
    public UserDTO updateUser(@Valid @RequestBody EditUserPayload editUserPayload) {
        return userService.updateUser(editUserPayload);
    }

    @DeleteMapping("/{userId}")
    @Secured({ROLE_ADMIN, ROLE_USER})
    public ResponseEntity<String> softDeleteUser(
            @Valid @Min (value = 1,message = "Invalid user id")
            @PathVariable("userId") Long userId) {
        return userService.softDeleteUser(userId);
    }

    @PostMapping
    @Secured({ROLE_ADMIN})
    public UserDTO createUser(@Valid @RequestBody UserCreationPayload userCreationPayload)
    {
        return userService.createUser(userCreationPayload);
    }

}
