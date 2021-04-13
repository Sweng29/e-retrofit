package com.retrofit.app.payload.request;

import java.util.Date;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SignUpPayload {

    @NotNull(message = "Username can not be null.")
    @NotEmpty(message = "Username can not be empty.")
    protected String username;

    @NotNull(message = "Email address can not be null.")
    @NotEmpty(message = "Email address can not be empty.")
    @Email
    protected String email;

    @NotNull(message = "Password can not be null.")
    @NotEmpty(message = "Password can not be empty.")
    protected String password;

    @NotNull(message = "Confirm Password can not be null.")
    @NotEmpty(message = "Confirm Password can not be empty.")
    protected String confirmPassword;

    @NotNull(message = "Mobile number can not be null.")
    @NotEmpty(message = "Mobile number can not be empty.")
    protected String mobileNumber;

    @NotNull(message = "Date of birth can not be null.")
    protected Date dateOfBirth;

}
