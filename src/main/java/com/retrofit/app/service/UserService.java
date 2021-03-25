package com.retrofit.app.service;

import com.retrofit.app.dto.UserDTO;
import com.retrofit.app.payload.SignUpPayload;

public interface UserService {

    UserDTO signUp(SignUpPayload signUpPayload);

}
