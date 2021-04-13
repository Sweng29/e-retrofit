package com.retrofit.app.payload.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserCreationPayload extends SignUpPayload{

    @NotNull(message = "Please provide a valid role id.")
    @Min(value = 1,message = "Role id must be greater then 0.")
    private Long roleId;

}
