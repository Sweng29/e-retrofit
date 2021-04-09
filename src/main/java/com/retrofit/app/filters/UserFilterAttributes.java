package com.retrofit.app.filters;

import com.retrofit.app.constants.ProfileStatus;
import com.retrofit.app.constants.RoleConstant;
import com.retrofit.app.payload.request.ERetrofitPageRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserFilterAttributes extends ERetrofitPageRequest {

    private Integer id;
    private RoleConstant roleConstant;
    private ProfileStatus profileStatus;
    private boolean isActive = Boolean.TRUE;

    public static UserFilterAttributes defaultFilter() {
        return new UserFilterAttributes();
    }

}
