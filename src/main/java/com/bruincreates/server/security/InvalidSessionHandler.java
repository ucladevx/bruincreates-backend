package com.bruincreates.server.security;

import com.bruincreates.server.model.response.RestResponse;
import com.bruincreates.server.utility.ServletUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class InvalidSessionHandler implements InvalidSessionStrategy {

    @Override
    public void onInvalidSessionDetected(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        log.info("Session expired: [{}]", request.getRequestURI());
        ServletUtils.render(request, response, RestResponse.fail("invalid session, try clear cookie"));
    }

}
