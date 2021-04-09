package com.retrofit.app.exception;

import com.retrofit.app.exception.error.UserErrorType;
import com.retrofit.app.helper.EntityHelper;
import com.retrofit.app.payload.response.Response;
import java.util.Collections;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory
            .getLogger(GlobalExceptionHandler.class);

    private void logException(String message, StringBuffer url, Exception e) {
        logger.error("********************** Start Global Exception **************");
        logger.error("URL: {}", url);
        logger.error(message, e);
        logger.error("********************** End Global Exception **************");
    }

    @ResponseBody
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    Response<Object> userNotFoundExceptionHandler(UserNotFoundException ex) {
        return Response
                .builder()
                .message(UserErrorType.USER_NOT_FOUND.getMessage())
                .responseCode(UserErrorType.USER_NOT_FOUND.getErrorCode())
                .build();
    }

    @ResponseBody
    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response<Object> nullPointerExceptionHandler(
            HttpServletRequest req,
            HttpServletResponse response,
            NullPointerException e) {

        logException(e.getMessage(), req.getRequestURL(), e);
        String message =
                EntityHelper.isNotNull(e.getMessage())
                        ? e.getMessage()
                        : "Server error. Please check with admin";
    return Response.builder()
        .responseCode(HttpStatus.BAD_REQUEST.value())
        .message(message)
        .result(Collections.emptyList())
        .build();
    }

    @ResponseBody
    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response<Object> badCredentialsExceptionHandler(
            HttpServletRequest req, HttpServletResponse response, Exception e){

        logException(e.getMessage(), req.getRequestURL(), e);
        String message =
                EntityHelper.isNotNull(e.getMessage())
                        ? e.getMessage()
                        : "Server error. Bad credentials provided.";
        return Response.builder()
                .responseCode(HttpStatus.BAD_REQUEST.value())
                .message(message)
                .result(Collections.emptyList())
                .build();
    }

    @ResponseBody
    @ExceptionHandler(DisabledException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response<Object> disabledUserExceptionHandler(
            HttpServletRequest req, HttpServletResponse response, Exception e){

        logException(e.getMessage(), req.getRequestURL(), e);
        String message =
                EntityHelper.isNotNull(e.getMessage())
                        ? e.getMessage()
                        : "Server error, User is disabled.";
        return Response.builder()
                .responseCode(HttpStatus.BAD_REQUEST.value())
                .message(message)
                .result(Collections.emptyList())
                .build();
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response<Object> globalExceptionHandler(
            HttpServletRequest req, HttpServletResponse response, Exception e){

        logException(e.getMessage(), req.getRequestURL(), e);
        String message =
                EntityHelper.isNotNull(e.getMessage())
                        ? e.getMessage()
                        : "Server error. Something went wrong.";
    return Response.builder()
        .responseCode(HttpStatus.BAD_REQUEST.value())
        .message(message)
        .result(Collections.emptyList())
        .build();
    }

    @ResponseBody
    @ExceptionHandler(InvalidInputException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response<Object> invalidInputExceptionHandler(
            HttpServletRequest req, HttpServletResponse response, Exception e){

        logException(e.getMessage(), req.getRequestURL(), e);
        String message =
                EntityHelper.isNotNull(e.getMessage())
                        ? e.getMessage()
                        : "Server error. Invalid request payload.";
        return Response.builder()
                .responseCode(HttpStatus.BAD_REQUEST.value())
                .message(message)
                .result(Collections.emptyList())
                .build();
    }

}
