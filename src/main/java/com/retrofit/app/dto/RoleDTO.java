package com.retrofit.app.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.retrofit.app.constants.RoleConstant;
import com.retrofit.app.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoleDTO {

    private Long roleId;
    private RoleConstant roleConstant;

    public static RoleDTO of(Role role)
    {
        if (role == null)
            return null;

        return RoleDTO
                .builder()
                .roleId(role.getId())
                .roleConstant(role.getRoleConstant())
                .build();
    }

}
