package com.retrofit.app.service;

import com.retrofit.app.dto.UserDTO;
import com.retrofit.app.filters.UserFilterAttributes;
import com.retrofit.app.model.User;
import com.retrofit.app.payload.request.EditUserPayload;
import com.retrofit.app.payload.request.SignUpPayload;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {

    UserDTO signUp(SignUpPayload signUpPayload);
    UserDTO findUserById(Long userId);
    UserDetails loadUserById(Long id);
    String authenticateUser(String usernameEmailOrMobileNumber,String password);
    List<UserDTO> findAllUsers(UserFilterAttributes filterAttributes);
    User findById(Long id);
    UserDTO findCurrentUserDetails();
    Boolean existsByUsernameOrEmailOrMobileNumber(String usernameEmailOrMobileNumber);
    UserDTO updateUser(EditUserPayload editUserPayload);
    ResponseEntity<String> softDeleteUser(Long userId);

}
