package com.bruincreates.server.exception;

import com.bruincreates.server.model.servlet.ResponseCode;
import com.bruincreates.server.model.servlet.RestResponse;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * GlobalExceptionHandler intercepts all exceptions and returns appropriate
 * http response and status based on exception category.
 *
 * ControllerAdvice is one of the AOP (Aspect Oriented Programming) feature
 * offered by the Spring framework, read more on yourself if interested.
 *
 * [Some exception handlers are not listed below because they have been
 *  handled using Servlet.render() method]
 *
 * @author aojiao
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UsernameNotFoundException.class)
    public RestResponse<String> exceptionHandler(UsernameNotFoundException e) {
        return RestResponse.fail(ResponseCode.USER_NOT_EXIST, e.getMessage());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public RestResponse<String> exceptionHandler(BadCredentialsException e) {
        return RestResponse.fail(ResponseCode.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(ClassCastException.class)
    public RestResponse<String> exceptionHandler(ClassCastException e) {
        return RestResponse.fail(ResponseCode.BAD_REQUEST, "Invalid Input");
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public RestResponse<String> exceptionHandler(DuplicateKeyException e) {
        return RestResponse.fail(ResponseCode.BAD_REQUEST, "Duplicate User");
    }

    @ExceptionHandler(DataAccessException.class)
    public RestResponse<String> exceptionHandler(DataAccessException e) {
        return RestResponse.fail(ResponseCode.BAD_REQUEST, "Error Querying Database");
    }

    @ExceptionHandler(Exception.class)
    public RestResponse<String> exceptionHandler(Exception e) {
        return RestResponse.fail(ResponseCode.FAIL, e.getMessage());
    }

}
