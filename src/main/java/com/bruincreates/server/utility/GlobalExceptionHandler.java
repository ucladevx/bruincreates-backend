package com.bruincreates.server.utility;

import com.bruincreates.server.model.servlet.ResponseCode;
import com.bruincreates.server.model.servlet.RestResponse;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

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


    @ExceptionHandler(Exception.class)
    public RestResponse<String> exceptionHandler(Exception e) {
        return RestResponse.fail(ResponseCode.FAIL, e.getMessage());
    }

}
