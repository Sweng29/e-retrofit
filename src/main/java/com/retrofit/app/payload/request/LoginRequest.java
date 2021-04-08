package com.retrofit.app.payload.request;

import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest implements Serializable {

    @NotBlank(message = "Username field can not be empty.")
    private String usernameOrEmailOrMobileNo;
    @NotBlank(message = "Password can not be empty.")
    private String password;

}
