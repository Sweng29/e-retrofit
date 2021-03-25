package com.retrofit.app.mapper;

import com.retrofit.app.dto.UserDTO;
import com.retrofit.app.model.User;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public interface UserMapper {

    static UserDTO map(User user)
    {
        if (user == null)
            return null;

        return UserDTO
                .builder()
                .userId(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .password(user.getPassword())
                .mobileNumber(user.getMobileNumber())
                .dateOfBirth(user.getDateOfBirth())
                .isActive(user.getIsActive())
                .build();
    }

    static List<UserDTO> map(List<User> userList)
    {
        if (userList == null || userList.isEmpty())
            return Collections.emptyList();

        return userList
                .stream()
                .map(UserMapper::map)
                .collect(Collectors.toList());
    }

}
