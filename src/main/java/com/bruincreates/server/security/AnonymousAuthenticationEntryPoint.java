package com.bruincreates.server.security;

import com.bruincreates.server.model.response.RestResponse;
import com.bruincreates.server.utility.ServletUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class AnonymousAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException {
        log.warn("Login required: [{}], AuthenticationException={}", request.getRequestURI(), e);
        ServletUtils.render(request, response, RestResponse.fail("login required"));
    }

}
