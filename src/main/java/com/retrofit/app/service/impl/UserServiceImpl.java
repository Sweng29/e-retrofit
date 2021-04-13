package com.retrofit.app.service.impl;

import com.retrofit.app.config.JwtTokenProvider;
import com.retrofit.app.constants.ProfileStatus;
import com.retrofit.app.dto.UserDTO;
import com.retrofit.app.exception.InvalidInputException;
import com.retrofit.app.exception.UserNotFoundException;
import com.retrofit.app.exception.error.UserErrorType;
import com.retrofit.app.filters.UserFilterAttributes;
import com.retrofit.app.helper.EntityHelper;
import com.retrofit.app.helper.PageUtils;
import com.retrofit.app.mapper.UserMapper;
import com.retrofit.app.model.Role;
import com.retrofit.app.model.User;
import com.retrofit.app.payload.request.EditUserPayload;
import com.retrofit.app.payload.request.SignUpPayload;
import com.retrofit.app.payload.request.UserCreationPayload;
import com.retrofit.app.repository.RoleRepository;
import com.retrofit.app.repository.UserRepository;
import com.retrofit.app.security.UserPrincipal;
import com.retrofit.app.service.BaseService;
import com.retrofit.app.service.UserService;
import com.retrofit.app.specifications.UserSpecification;
import com.retrofit.app.validator.UserValidator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl extends BaseService implements UserService, UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDTO signUp(SignUpPayload signUpPayload) {

        boolean isValidPayload = UserValidator.validateSignUpRequestPayload(signUpPayload);

        boolean emailExists = validateEmailExists(signUpPayload.getEmail());

        boolean mobileNumberExists = validateMobileNumberExists(signUpPayload.getMobileNumber());

        boolean usernameExists = validateUsernameExists(signUpPayload.getUsername());

        if (!isValidPayload)
            throw new InvalidInputException("Invalid request payload");

        if (emailExists)
            throw new InvalidInputException("Invalid request payload - email already exists.");

        if (mobileNumberExists)
            throw new InvalidInputException("Invalid request payload - mobile number already exists.");

        if (usernameExists)
            throw new InvalidInputException("Invalid request payload - username already exists.");

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

    @Override
    public UserDTO findUserById(Long userId) {
    User user =
        userRepository
            .findById(userId)
            .orElseThrow(
                () -> new UserNotFoundException(UserErrorType.USER_NOT_FOUND.getMessage()));
        return UserMapper.map(user);
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
        // Let people login with either username or email
        User user = userRepository.findByUsernameOrEmailOrMobileNumber(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with username, email or mobile"
                                + " number : " + username)
                );

        return UserPrincipal.create(user);

    }

    @Override
    @Transactional
    public UserDetails loadUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("User not found with id : " + id)
        );

        return UserPrincipal.create(user);
    }

    @Override
    public String authenticateUser(String usernameEmailOrMobileNumber,String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        usernameEmailOrMobileNumber,
                        password
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtTokenProvider.generateToken(authentication);
    }

    @Override
    public List<UserDTO> findAllUsers(UserFilterAttributes filterAttributes) {
        Pageable pageable = PageUtils.createPageWithSort(filterAttributes);
        Page<User> page = userRepository.findAll(UserSpecification.findAll(filterAttributes), pageable);
        return page.map(UserMapper::map).getContent();
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public UserDTO findCurrentUserDetails() {
        User user =  getLoggedInUser();
        return UserMapper.map(user);
    }

    @Override
    public Boolean existsByUsernameOrEmailOrMobileNumber(String usernameEmailOrMobileNumber) {
        return userRepository
                .findByUsernameOrEmailOrMobileNumber(usernameEmailOrMobileNumber)
                .isPresent();
    }

    @Override
    public UserDTO updateUser(EditUserPayload editUserPayload) {
        boolean validateEditPayload = UserValidator.validateEditUserPayload(editUserPayload);

        User loggedInUser = getLoggedInUser();
        if (!validateEditPayload)
            throw new InvalidInputException("Invalid payload data, please try again.");

        if (!loggedInUser.getId().equals(editUserPayload.getUserId()))
            throw new InvalidInputException("Unauthorized access, "
                    + "you are not allowed to perform this operation.");

        User editableUser = findById(editUserPayload.getUserId());
        updateNonRestrictedUserDetails(editableUser,editUserPayload);
        if (isAdminLoggedIn())
            updateRestrictedUserDetails(editableUser,editUserPayload);

        return UserMapper.map(userRepository.save(editableUser));
    }

    @Override
    public ResponseEntity<String> softDeleteUser(Long userId) {
        User user = findById(userId);
        user.setIsActive(Boolean.FALSE);
        userRepository.save(user);
        return ResponseEntity.ok("User has been deleted successfully.");
    }

    @Override
    public UserDTO createUser(UserCreationPayload userCreationPayload) {
        boolean isValidPayload = UserValidator.validateUserCreationRequestPayload(userCreationPayload);

        boolean emailExists = validateEmailExists(userCreationPayload.getEmail());

        boolean mobileNumberExists = validateMobileNumberExists(userCreationPayload.getMobileNumber());

        boolean usernameExists = validateUsernameExists(userCreationPayload.getUsername());

        Role role = roleRepository
                .findById(userCreationPayload.getRoleId())
                .orElseThrow(() -> new InvalidInputException("No role found with given id."));

        if (!isValidPayload)
            throw new InvalidInputException("Invalid request payload");

        if (emailExists)
            throw new InvalidInputException("Invalid request payload - email already exists.");

        if (mobileNumberExists)
            throw new InvalidInputException("Invalid request payload - mobile number already exists.");

        if (usernameExists)
            throw new InvalidInputException("Invalid request payload - username already exists.");

        User user = User
                .builder()
                .dateOfBirth(userCreationPayload.getDateOfBirth())
                .email(userCreationPayload.getEmail())
                .mobileNumber(userCreationPayload.getMobileNumber())
                .username(userCreationPayload.getUsername())
                .password(passwordEncoder.encode(userCreationPayload.getPassword()))
                .isActive(Boolean.FALSE)
                .profileStatus(ProfileStatus.APPROVAL_PENDING)
                .role(role)
                .build();

        return UserMapper.map(userRepository.save(user));
    }

    private void updateNonRestrictedUserDetails(User editableUser, EditUserPayload editUserPayload) {
        editableUser.setDateOfBirth(
                EntityHelper.getNotNull(
                        editableUser.getDateOfBirth(),
                        editUserPayload.getDateOfBirth())
        );

        if (UserValidator.isMobileNumberValid(editUserPayload.getMobileNumber()) &&
                userRepository.existsByMobileNumber(editUserPayload.getMobileNumber()))
            editableUser.setMobileNumber(editUserPayload.getMobileNumber());

        if (isPasswordValid(editUserPayload.getPassword(),editUserPayload.getConfirmPassword()))
            editableUser.setMobileNumber(passwordEncoder.encode(editUserPayload.getPassword()));
    }

    private boolean isPasswordValid(String password, String confirmPassword) {
        return (password != null && confirmPassword != null) && password.equals(confirmPassword);
    }

    private void updateRestrictedUserDetails(User editableUser, EditUserPayload editUserPayload) {
        editableUser.setIsActive(
                EntityHelper.getNotNull(
                        editableUser.getIsActive(),
                        editUserPayload.getIsActive()
                )
        );

        editableUser.setProfileStatus(
                EntityHelper.getNotNull(
                        editableUser.getProfileStatus(),
                        editUserPayload.getProfileStatus()
                )
        );
    }

}
