package com.bruincreates.server.service;

import com.bruincreates.server.dao.mapper.UserMapper;
import com.bruincreates.server.dao.po.User;
import com.bruincreates.server.dao.po.UserExample;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    public User findUserAuthByUsername(String username) {

        UserExample userExample = new UserExample();
        userExample.createCriteria().andUsernameEqualTo(username);

        List<User> userList = userMapper.selectByExample(userExample);
        return userList != null ? userList.size() > 0 ? userList.get(0) : null : null;

    }

    @Transactional(rollbackFor = Exception.class)
    public void register() {
        User user = new User();
        user.setUsername("test");
        user.setPassword(passwordEncoder.encode("123456"));
        user.setEmail("test@gmail.com");
        userMapper.insertSelective(user);
    }

}
