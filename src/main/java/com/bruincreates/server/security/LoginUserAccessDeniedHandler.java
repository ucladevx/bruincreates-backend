package com.bruincreates.server.security;

import com.bruincreates.server.model.servlet.ResponseCode;
import com.bruincreates.server.model.servlet.RestResponse;
import com.bruincreates.server.utility.ServletUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class LoginUserAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        log.warn("Access denied: [{}], AccessDeniedException={}", request.getRequestURI(), accessDeniedException);
        ServletUtils.render(request, response, RestResponse.build(ResponseCode.NO_AUTHORIZATION));
    }

}