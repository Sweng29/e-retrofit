package com.retrofit.app.service.impl;

import com.retrofit.app.dto.UserDTO;
import com.retrofit.app.payload.SignUpPayload;
import com.retrofit.app.repository.UserRepository;
import com.retrofit.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDTO signUp(SignUpPayload signUpPayload) {
        return null;
    }
}
