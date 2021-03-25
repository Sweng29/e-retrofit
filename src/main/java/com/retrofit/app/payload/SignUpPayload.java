package com.retrofit.app.payload;

import java.util.Date;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class SignUpPayload {

    @NotNull(message = "Username can not be null.")
    @NotEmpty(message = "Username can not be empty.")
    @Min(value = 3,message = "Username should be at least 3 characters long.")
    private String username;

    @NotNull(message = "Email address can not be null.")
    @NotEmpty(message = "Email address can not be empty.")
    private String email;

    @NotNull(message = "Password can not be null.")
    @NotEmpty(message = "Password can not be empty.")
    @Min(value = 6,message = "Password should be at least 6 characters long.")
    private String password;

    @NotNull(message = "Mobile number can not be null.")
    @NotEmpty(message = "Mobile number can not be empty.")
    private String mobileNumber;

    @NotNull(message = "Date of birth can not be null.")
    @NotEmpty(message = "Date of birth can not be empty.")
    private Date dateOfBirth;

}
