package com.retrofit.app.service;

import com.retrofit.app.constants.RoleConstant;
import com.retrofit.app.model.User;
import com.retrofit.app.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public abstract class BaseService {

    @Autowired
    protected UserService userService;

    protected User getLoggedInUser() {
        UserPrincipal userPrincipal = getPrincipal();
        return findById(userPrincipal.getId());
    }

    protected boolean adminIsNotLoggedIn() {
        return !isAdminLoggedIn();
    }

    protected boolean isAdminLoggedIn() {
        return isUserLoggedIn() &&
                RoleConstant.ROLE_ADMIN.equals(getLoggedInUser().getRole().getRoleConstant());
    }

    protected boolean isEndUserLoggedIn() {
        return isUserLoggedIn() &&
                RoleConstant.ROLE_USER.equals(getLoggedInUser().getRole().getRoleConstant());
    }

    public User findById(Long id) {
        return userService.findById(id);
    }

    private UserPrincipal getPrincipal() {
        return (UserPrincipal) SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getPrincipal();
    }

    private boolean isUserLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return !(authentication instanceof AnonymousAuthenticationToken);
    }
}