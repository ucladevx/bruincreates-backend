package com.bruincreates.server.controller;

import com.bruincreates.server.exception.BadRequestException;
import com.bruincreates.server.model.request.RegistrationRequest;
import com.bruincreates.server.model.servlet.RestResponse;
import com.bruincreates.server.model.user.UserControlBlock;
import com.bruincreates.server.service.AccountService;
import com.bruincreates.server.utility.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Account Controller is responsible for all user account
 * management service. Importantly, this api is exposed to
 * the general public and a Spring session is not established
 */

@Slf4j
@RestController
@RequestMapping("/api/account")
public class AccountController {

    @Autowired
    AccountService accountService;

    @PostMapping("/register")
    public RestResponse<UserControlBlock> register(@Valid @RequestBody RegistrationRequest request) throws BadRequestException {
        accountService.register(request);
        return RestResponse.success();
    }

    @GetMapping("/verifyEmail")
    public RestResponse<String> verifyEmail(@RequestParam("jwt") String jwt) {
        String username = JwtUtil.parseToken(jwt);
        accountService.verifyEmail(username);
        return RestResponse.success("Email Verified");
    }

    @PostMapping("/sendResetUrl")
    public RestResponse<String> sendPasswordResetUrl() {
        //get username, get email
        //send link (generate jwt + url)
        //and return
        return RestResponse.success("Password Reset Link Sent");
    }

    @PostMapping("/resetPassword")
    public RestResponse<String> resetPassword() {
        //get password, jwt
        //get username from jwt
        //update username, password
        //and return
        return RestResponse.success("Password Reset Succeed");
    }

}
