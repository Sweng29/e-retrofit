package com.retrofit.app.validator;

import com.retrofit.app.payload.SignUpPayload;
import org.springframework.util.ObjectUtils;

public class UserValidator {

    private UserValidator(){}

    public static boolean validateSignUpRequestPayload(SignUpPayload signUpPayload)
    {
        if (ObjectUtils.isEmpty(signUpPayload))
        {
            return false;
        }

        return (!ObjectUtils.isEmpty(signUpPayload.getPassword()) &&
                !ObjectUtils.isEmpty(signUpPayload.getConfirmPassword())) &&
                signUpPayload.getPassword().equals(signUpPayload.getConfirmPassword());
    }

}
