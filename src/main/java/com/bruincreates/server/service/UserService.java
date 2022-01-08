package com.bruincreates.server.service;

import com.bruincreates.server.dao.mapper.UserAuthMapper;
import com.bruincreates.server.dao.mapper.UserInfoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {

    @Autowired
    UserInfoMapper userInfoMapper;

    @Autowired
    UserAuthMapper userAuthMapper;

}
