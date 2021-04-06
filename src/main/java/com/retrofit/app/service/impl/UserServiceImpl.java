package com.retrofit.app.service.impl;

import com.retrofit.app.constants.ProfileStatus;
import com.retrofit.app.dto.UserDTO;
import com.retrofit.app.mapper.UserMapper;
import com.retrofit.app.model.User;
import com.retrofit.app.payload.request.SignUpPayload;
import com.retrofit.app.repository.UserRepository;
import com.retrofit.app.service.UserService;
import com.retrofit.app.validator.UserValidator;
import java.util.ArrayList;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

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
                .password(passwordEncoder.encode(signUpPayload.getPassword()))
                .isActive(Boolean.FALSE)
                .profileStatus(ProfileStatus.APPROVAL_PENDING)
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

    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return new org.springframework.security.core.userdetails.User(
                user.get().getUsername(),
                user.get().getPassword(),
                new ArrayList<>());

    }
}
