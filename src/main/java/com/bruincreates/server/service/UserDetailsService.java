package com.bruincreates.server.service;

import com.bruincreates.server.dao.po.User;
import com.bruincreates.server.model.user.UserControlBlock;
import com.bruincreates.server.utility.IpUtils;
import com.bruincreates.server.utility.ServletUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AccountService accountService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        if (username == null || username.length() < 1) {
            log.info("[loadUserByUsername]: username is empty");
            throw new UsernameNotFoundException("USER_NOT_EXIST");
        }

        User user = accountService.findUserByUsername(username);

        if (user == null) {
            log.info("[loadUserByUsername]: user doesn't exist for {}", username);
            throw new UsernameNotFoundException("USER_NOT_EXIST");
        }

        return new UserControlBlock(user, IpUtils.getIpAddr(ServletUtils.getRequest()), LocalDateTime.now());
    }

}
