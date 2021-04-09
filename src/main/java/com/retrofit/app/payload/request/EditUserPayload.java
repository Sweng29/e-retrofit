package com.retrofit.app.payload.request;

import com.retrofit.app.constants.ProfileStatus;
import java.util.Date;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class EditUserPayload {

    private Long userId;
    private String username;
    private String password;
    private String confirmPassword;
    private String mobileNumber;
    private Date dateOfBirth;
    private ProfileStatus profileStatus;
    private Boolean isActive;
}
