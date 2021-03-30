package com.retrofit.app.service.impl;

import com.retrofit.app.dto.UserDTO;
import com.retrofit.app.mapper.UserMapper;
import com.retrofit.app.model.User;
import com.retrofit.app.payload.SignUpPayload;
import com.retrofit.app.repository.UserRepository;
import com.retrofit.app.service.UserService;
import com.retrofit.app.validator.UserValidator;
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

        boolean isValidPayload = UserValidator.validateSignUpRequestPayload(signUpPayload);

        boolean emailExists = validateEmailExists(signUpPayload.getEmail());

        boolean mobileNumberExists = validateMobileNumberExists(signUpPayload.getMobileNumber());

        boolean usernameExists = validateUsernameExists(signUpPayload.getUsername());

        if (!isValidPayload)
            return null;

        if (emailExists)
            return null;

        if (mobileNumberExists)
            return null;

        if (usernameExists)
            return null;

        User user = User
                .builder()
                .dateOfBirth(signUpPayload.getDateOfBirth())
                .email(signUpPayload.getEmail())
                .mobileNumber(signUpPayload.getMobileNumber())
                .username(signUpPayload.getUsername())
                .password(signUpPayload.getPassword())
                .isActive(Boolean.FALSE)
                .build();

        return UserMapper.map(userRepository.save(user));
    }

    private boolean validateUsernameExists(String username) {
        return userRepository.findByUsername(username)
                .isPresent();
    }

    private boolean validateMobileNumberExists(String mobileNumber) {
       return userRepository.findByMobileNumber(mobileNumber)
               .isPresent();
    }

    private boolean validateEmailExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }
}
