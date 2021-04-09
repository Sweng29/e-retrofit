package com.retrofit.app.exception.error;

import lombok.Getter;

@Getter
public enum UserErrorType implements ApiErrorType{

    USER_NOT_AVAILABLE_WITH_ID(1001,
            "User does not exist."),
    USER_ALREADY_EXISTS(1002,
            "User already exists."),
    USER_WITH_EMAIL_ALREADY_REGISTERED(1003,
            "A user already has this email address."),
    USER_WITH_PHONE_ALREADY_EXISTS(1004,
            "A user already has this phone number."),
    USER_NOT_FOUND(1005,"User not found.");

    private Integer errorCode;
    private String message;

    UserErrorType(Integer errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    @Override
    public Integer getErrorCode() {
        return errorCode;
    }

    @Override
    public String getErrorMessage() {
        return message;
    }
}
