package com.retrofit.app.service;

import com.retrofit.app.dto.UserDTO;
import com.retrofit.app.payload.request.SignUpPayload;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {

    UserDTO signUp(SignUpPayload signUpPayload);
    UserDTO findUserById(Long userId);
    UserDetails loadUserById(Long id);
    String authenticateUser(String usernameEmailOrMobileNumber,String password);

}
