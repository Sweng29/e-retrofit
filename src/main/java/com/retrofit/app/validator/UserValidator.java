package com.retrofit.app.validator;

import com.retrofit.app.payload.request.EditUserPayload;
import com.retrofit.app.payload.request.SignUpPayload;
import com.retrofit.app.payload.request.UserCreationPayload;
import org.springframework.util.ObjectUtils;

public interface UserValidator {

    static boolean validateSignUpRequestPayload(SignUpPayload signUpPayload)
    {
        if (ObjectUtils.isEmpty(signUpPayload))
        {
            return false;
        }

        return (!ObjectUtils.isEmpty(signUpPayload.getPassword()) &&
                !ObjectUtils.isEmpty(signUpPayload.getConfirmPassword())) &&
                signUpPayload.getPassword().equals(signUpPayload.getConfirmPassword());
    }

    static boolean validateEditUserPayload(EditUserPayload editUserPayload) {
        boolean isValid = Boolean.TRUE;
        if (ObjectUtils.isEmpty(editUserPayload))
            isValid = Boolean.FALSE;

        if (ObjectUtils.isEmpty(editUserPayload.getUserId()))
            isValid = Boolean.FALSE;

        return isValid;
    }

    static boolean isMobileNumberValid(String mobileNumber) {
        return mobileNumber!=null;
    }

    static boolean validateUserCreationRequestPayload(
            UserCreationPayload userCreationPayload) {

        if (ObjectUtils.isEmpty(userCreationPayload))
        {
            return false;
        }

        return (!ObjectUtils.isEmpty(userCreationPayload.getPassword()) &&
                !ObjectUtils.isEmpty(userCreationPayload.getConfirmPassword())) &&
                !ObjectUtils.isEmpty(userCreationPayload.getRoleId()) &&
                userCreationPayload.getPassword().equals(userCreationPayload.getConfirmPassword());
    }
}
