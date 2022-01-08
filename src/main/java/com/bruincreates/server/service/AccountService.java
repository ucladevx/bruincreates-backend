package com.bruincreates.server.service;

import com.bruincreates.server.dao.mapper.UserMapper;
import com.bruincreates.server.dao.po.User;
import com.bruincreates.server.dao.po.UserExample;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class AccountService {

    @Autowired
    UserMapper userMapper;

    public User findUserAuthByUsername(String username) {

        UserExample userExample = new UserExample();
        userExample.createCriteria().andUsernameEqualTo(username);

        List<User> userList = userMapper.selectByExample(userExample);
        return userList != null ? userList.size() > 0 ? userList.get(0) : null : null;
    }

}
