package com.bruincreates.server.configuration;

import com.bruincreates.server.security.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

/**
 * WebSecurityConfig sets up security framework using
 * Spring Security and Spring Session Redis
 *
 * [DO NOT MODIFY UNDER ANY CIRCUMSTANCES]
 *
 * @author aojiao
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    LoginSuccessHandler loginSuccessHandler;

    @Autowired
    LoginFailureHandler loginFailureHandler;

    @Autowired
    AnonymousAuthenticationEntryPoint anonymousAuthenticationEntryPoint;

    @Autowired
    AccessDeniedHandler accessDeniedHandler;

    @Autowired
    LogoutSuccessHandler logoutSuccessHandler;

    @Autowired
    InvalidSessionHandler invalidSessionHandler;

    @Autowired
    SessionInformationExpiredHandler sessionInformationExpiredHandler;

    public static final String LOGIN_URL = "/api/account/login";

    public static final String LOGOUT_URL = "/api/account/register";

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable();
        http.authorizeRequests()
                // released and protected api
                .antMatchers(LOGIN_URL).permitAll()
                .antMatchers(LOGOUT_URL).permitAll()
                .anyRequest().authenticated()
                // exception management
                .and().exceptionHandling()
                .authenticationEntryPoint(anonymousAuthenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler)
                // login management
                .and().formLogin().permitAll()
                .successHandler(loginSuccessHandler)
                .failureHandler(loginFailureHandler)
                // logout management
                .and().logout().permitAll()
                .logoutSuccessHandler(logoutSuccessHandler)
                .deleteCookies(RestHttpSessionIdResolver.AUTH_TOKEN)
                // session management
                .and().sessionManagement()
                .invalidSessionStrategy(invalidSessionHandler)
                .maximumSessions(1)
                .expiredSessionStrategy(sessionInformationExpiredHandler);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.authenticationProvider(authProvider());
    }

    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new Argon2PasswordEncoder();
    }

}
