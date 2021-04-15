package com.retrofit.app.exception.error;

public enum ProductErrorType implements ApiErrorType{

    PRODUCT_NOT_AVAILABLE_WITH_ID(1001,
            "Product does not exist."),
    PRODUCT_ALREADY_EXISTS(1002,
            "Product already exists.");

    private Integer errorCode;
    private String message;

    ProductErrorType(Integer errorCode, String message) {
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
