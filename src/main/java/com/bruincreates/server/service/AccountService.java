package com.bruincreates.server.service;

import com.bruincreates.server.dao.mapper.UserMapper;
import com.bruincreates.server.dao.po.User;
import com.bruincreates.server.dao.po.UserExample;
import com.bruincreates.server.exception.BadRequestException;
import com.bruincreates.server.model.request.PasswordResetRequest;
import com.bruincreates.server.model.request.PasswordResetUrlRequest;
import com.bruincreates.server.model.request.RegistrationRequest;
import com.bruincreates.server.model.request.UsernameResetRequest;
import com.bruincreates.server.model.request.EmailResetRequest;
import com.bruincreates.server.model.request.ProfileNameResetRequest;
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

    public User findUserByEmail(String email) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andEmailEqualTo(email);
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

    public void sendPasswordResetURL(PasswordResetUrlRequest request) throws BadRequestException{
        //validate email
        boolean validEmail = EmailValidator.getInstance().isValid(request.getEmail());
        if (!validEmail) {
            throw new BadRequestException("wrong email format");
        }

        //valid user exists
        User user = findUserByEmail(request.getEmail());
        if (user == null) {
            throw new BadRequestException("user not found");
        }

        //generate jwt token
        String jwt = JwtUtil.createToken(user.getUsername(), "user");
        String passwordResetUrl = "localhost:3000/reset/" + jwt;

        //send reset url
        emailService.sendSimpleEmail(request.getEmail(),
                "BruinCreates: Please click on the link to reset your password.",
                "Password Reset Link: " + passwordResetUrl);
    }

    public void resetPassword(PasswordResetRequest request) throws BadRequestException{
        //parse jwt
        String jwt=request.getJwt();
        String password=request.getPassword();
        String username=JwtUtil.parseToken(jwt);

        //update password
        UserExample userExample = new UserExample();
        userExample.createCriteria().andUsernameEqualTo(username);
        User user = new User();
        user.setPassword(passwordEncoder.encode(password));
        userMapper.updateByExampleSelective(user, userExample);
    }

    public void updateUsername(UsernameResetRequest request) throws BadRequestException {

        //1. bad username
        if(request.getNewUsername().equals("")) {
            throw new BadRequestException("invalid new username");
        }

        //2. if that username is already taken
        User alreadyTaken = findUserByUsername(request.getNewUsername());
        if(alreadyTaken != null ) {
            throw new BadRequestException("username already taken");
        }

        UserExample userExample = new UserExample();
        userExample.createCriteria().andUsernameEqualTo(request.getOldUsername());
        User user = new User();
        user.setUsername(request.getNewUsername());
        userMapper.updateByExampleSelective(user, userExample);
    }

    public void updateEmail(EmailResetRequest request) throws BadRequestException {

        //1. validate email
        boolean validEmail = EmailValidator.getInstance().isValid(request.getNewEmail());
        if (!validEmail) {
            throw new BadRequestException("wrong email format");
        }

        //2. if new email is already taken
        User alreadyTaken = findUserByEmail(request.getNewEmail());
        if(alreadyTaken != null ) {
            throw new BadRequestException("email already taken");
        }

        //get username using the old email
        User temp = findUserByEmail(request.getOldEmail());
        String username = temp.getUsername();

        //update the email
        UserExample userExample = new UserExample();
        userExample.createCriteria().andUsernameEqualTo(username);
        User user = new User();
        user.setEmail(request.getNewEmail());
        userMapper.updateByExampleSelective(user, userExample);
    }

    public void updateProfileName(ProfileNameResetRequest request) throws BadRequestException {

        //1. empty new profile name
        if(request.getNewProfileName().equals("")) {
            throw new BadRequestException("invalid new profile name");
        }

        //get username using the email
        User temp = findUserByEmail(request.getEmail());
        String username = temp.getUsername();

        //set new profile name
        UserExample userExample = new UserExample();
        userExample.createCriteria().andUsernameEqualTo(username);
        User user = new User();
        user.setProfileName(request.getNewProfileName());
        userMapper.updateByExampleSelective(user, userExample);
    }
}
