package com.bruincreates.server.security;

import com.bruincreates.server.model.servlet.ResponseCode;
import com.bruincreates.server.model.servlet.RestResponse;
import com.bruincreates.server.utility.ServletUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class LoginFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse response, AuthenticationException e) throws IOException {
        log.warn("Login failed: [{}], AuthenticationException={}", httpServletRequest.getRequestURI(), e);
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Headers", "token, Accept, Origin, X-Requested-With, Content-Type, Last-Modified");
        response.setHeader("Content-type", "application/json;charset=UTF-8");
        ServletUtils.render(httpServletRequest, response, RestResponse.fail(ResponseCode.USER_LOGIN_FAIL));
    }

}
