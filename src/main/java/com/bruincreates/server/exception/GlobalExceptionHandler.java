package com.bruincreates.server.exception;

import com.bruincreates.server.model.response.RestResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

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

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public RestResponse<String> exceptionHandler(BadRequestException e) {
        return RestResponse.fail(e.message);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public RestResponse<String> exceptionHandler(Exception e) {
        return RestResponse.fail(e.getMessage());
    }

}
