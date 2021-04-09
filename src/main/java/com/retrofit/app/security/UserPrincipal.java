package com.retrofit.app.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.retrofit.app.constants.ProfileStatus;
import com.retrofit.app.model.User;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserPrincipal implements UserDetails {

    private Long id;
    private String username;
    @JsonIgnore
    private String email;
    @JsonIgnore
    private String password;
    @JsonIgnore
    private String mobileNumber;
    private Boolean isActive;
    private ProfileStatus profileStatus;
    private Collection<? extends GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return (isActive && ProfileStatus.APPROVED.equals(this.getProfileStatus()));
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isActive;
    }

    public static UserPrincipal create(User user) {
    List<GrantedAuthority> authorities = new ArrayList<>(
            Collections.singleton(
                    new SimpleGrantedAuthority(user.getRole().getRoleConstant().name())
            )
    );

        return new UserPrincipal(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                user.getMobileNumber(),
                user.getIsActive(),
                user.getProfileStatus(),
                authorities
        );
    }

}
