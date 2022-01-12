package com.bruincreates.server.security;

import com.bruincreates.server.model.response.ResponseCode;
import com.bruincreates.server.model.response.RestResponse;
import com.bruincreates.server.utility.ServletUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class SessionInformationExpiredHandler implements SessionInformationExpiredStrategy {

    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent sessionInformationExpiredEvent) throws IOException {
        log.error("Session terminated early: [{}]", sessionInformationExpiredEvent.toString());
        ServletUtils.render(sessionInformationExpiredEvent.getRequest(),
                sessionInformationExpiredEvent.getResponse(), RestResponse.fail(ResponseCode.INVALID_USER_SESSION));
    }

}