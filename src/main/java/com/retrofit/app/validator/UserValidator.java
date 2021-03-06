package com.retrofit.app.validator;

import com.retrofit.app.payload.request.EditUserPayload;
import com.retrofit.app.payload.request.SignUpPayload;
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

    public static boolean validateEditUserPayload(EditUserPayload editUserPayload) {
        boolean isValid = Boolean.TRUE;
        if (ObjectUtils.isEmpty(editUserPayload))
            isValid = Boolean.FALSE;

        if (ObjectUtils.isEmpty(editUserPayload.getUserId()))
            isValid = Boolean.FALSE;

        return isValid;
    }

    public static boolean isMobileNumberValid(String mobileNumber) {
        return mobileNumber!=null;
    }
}
