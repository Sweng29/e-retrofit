package com.retrofit.app.payload.request;

import com.retrofit.app.constants.ProfileStatus;
import java.util.Date;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class EditUserPayload {

    @Min(value = 1,message = "User id must be greater then 0.")
    @NotNull(message = "User id can not be null.")
    private Long userId;
    private String username;
    private String password;
    private String confirmPassword;
    private String mobileNumber;
    private Date dateOfBirth;
    private ProfileStatus profileStatus;
    private Boolean isActive;
}
