package com.bruincreates.server.service;

import com.bruincreates.server.dao.mapper.UserMapper;
import com.bruincreates.server.dao.po.User;
import com.bruincreates.server.dao.po.UserExample;
import com.bruincreates.server.model.request.RegistrationRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class AccountService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    public User findUserByUsername(String username) {

        UserExample userExample = new UserExample();
        userExample.createCriteria().andUsernameEqualTo(username);
        List<User> userList = userMapper.selectByExample(userExample);
        return userList != null ? userList.size() > 0 ? userList.get(0) : null : null;

    }

    @Transactional(rollbackFor = Exception.class)
    public void register(RegistrationRequest request){

        boolean validEmail = EmailValidator.getInstance().isValid(request.getEmail());
        if (!validEmail) {
            throw new BadCredentialsException("Registration Error: Invalid Email");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setProfileName(request.getProfileName());
        userMapper.insertSelective(user);

    }

}
