package com.bruincreates.server.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.session.web.http.CookieHttpSessionIdResolver;
import org.springframework.session.web.http.DefaultCookieSerializer;
import org.springframework.session.web.http.HttpSessionIdResolver;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service("httpSessionIdResolver")
public class RestHttpSessionIdResolver implements HttpSessionIdResolver {

    public static final String AUTH_TOKEN = "BruinCreatesSessionId";

    private String sessionIdName = AUTH_TOKEN;

    private CookieHttpSessionIdResolver cookieHttpSessionIdResolver;

    public RestHttpSessionIdResolver() {
        initCookieHttpSessionIdResolver();
    }

    public RestHttpSessionIdResolver(String sessionIdName) {
        this.sessionIdName = sessionIdName;
        initCookieHttpSessionIdResolver();
    }

    public void initCookieHttpSessionIdResolver() {
        this.cookieHttpSessionIdResolver = new CookieHttpSessionIdResolver();
        DefaultCookieSerializer cookieSerializer = new DefaultCookieSerializer();
        cookieSerializer.setCookieName(this.sessionIdName);
        this.cookieHttpSessionIdResolver.setCookieSerializer(cookieSerializer);
    }

    @Override
    public List<String> resolveSessionIds(HttpServletRequest httpServletRequest) {
        // cookie
        List<String> cookies = cookieHttpSessionIdResolver.resolveSessionIds(httpServletRequest);
        if (cookies != null && !cookies.isEmpty()) {
            return cookies;
        }
        // header
        String headerValue = httpServletRequest.getHeader(this.sessionIdName);
        if (headerValue != null && !headerValue.isEmpty()) {
            return Collections.singletonList(headerValue);
        }
        // request parameter
        String sessionId = httpServletRequest.getParameter(this.sessionIdName);
        return (sessionId != null) ? Collections.singletonList(sessionId) : Collections.emptyList();
    }

    @Override
    public void setSessionId(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, String sessionId) {
        log.info(AUTH_TOKEN + "={}", sessionId);
        httpServletResponse.setHeader(this.sessionIdName, sessionId);
        this.cookieHttpSessionIdResolver.setSessionId(httpServletRequest, httpServletResponse, sessionId);
    }

    @Override
    public void expireSession(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        httpServletResponse.setHeader(this.sessionIdName, "");
        this.cookieHttpSessionIdResolver.setSessionId(httpServletRequest, httpServletResponse, "");
    }
}
