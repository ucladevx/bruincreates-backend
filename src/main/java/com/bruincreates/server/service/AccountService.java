package com.bruincreates.server.service;

import com.bruincreates.server.dao.mapper.UserMapper;
import com.bruincreates.server.dao.po.User;
import com.bruincreates.server.dao.po.UserExample;
import com.bruincreates.server.exception.BadRequestException;
import com.bruincreates.server.model.request.RegistrationRequest;
import com.bruincreates.server.utility.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class AccountService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    EmailService emailService;

    public User findUserByUsername(String username) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andUsernameEqualTo(username);
        List<User> userList = userMapper.selectByExample(userExample);
        return userList != null ? userList.size() > 0 ? userList.get(0) : null : null;
    }

    public void register(RegistrationRequest request) throws BadRequestException {
        //validate email
        boolean validEmail = EmailValidator.getInstance().isValid(request.getEmail());
        if (!validEmail) {
            throw new BadRequestException("wrong email format");
        }

        //construct user object
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setProfileName(request.getProfileName());

        //create user
        try {
            userMapper.insertSelective(user);
        } catch (Exception e) {
            throw new BadRequestException("duplicate username or email");
        }

        //generate jwt token
        String jwt = JwtUtil.createToken(user.getUsername(), "user");
        String verificationUrl = "localhost:8080/api/account/verifyEmail?jwt=" + jwt;

        //send verification email
        emailService.sendSimpleEmail(user.getEmail(),
                "BruinCreates: Please Verify Your Email",
                "Email Verification Link: " + verificationUrl);
    }

    public void verifyEmail(String username) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andUsernameEqualTo(username);
        User user = new User();
        user.setVerified(true);
        userMapper.updateByExampleSelective(user, userExample);
    }

}
