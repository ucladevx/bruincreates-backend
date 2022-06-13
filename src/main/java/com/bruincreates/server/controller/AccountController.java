package com.bruincreates.server.controller;

import com.bruincreates.server.exception.BadRequestException;
import com.bruincreates.server.model.request.PasswordResetRequest;
import com.bruincreates.server.model.request.PasswordResetUrlRequest;
import com.bruincreates.server.model.request.RegistrationRequest;
import com.bruincreates.server.model.request.UsernameResetRequest;
import com.bruincreates.server.model.request.EmailResetRequest;
import com.bruincreates.server.model.request.ProfileNameResetRequest;
import com.bruincreates.server.model.response.RestResponse;
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
        return RestResponse.success("email verified");
    }

    @PostMapping("/sendResetUrl")
    public RestResponse<String> sendPasswordResetUrl(@Valid @RequestBody PasswordResetUrlRequest request) throws BadRequestException {
        accountService.sendPasswordResetURL(request);
        return RestResponse.success("password reset link sent");
    }

    @PostMapping("/resetPassword")
    public RestResponse<String> resetPassword(@Valid @RequestBody PasswordResetRequest request) throws BadRequestException {
        accountService.resetPassword(request);
        return RestResponse.success("password reset success");
    }

    @PutMapping("/updateUsername")
    public RestResponse<String> updateUsername(@Valid @RequestBody UsernameResetRequest request) throws BadRequestException {
        accountService.updateUsername(request);
        return RestResponse.success("username update success");
    }

    @PutMapping("/updateEmail")
    public RestResponse<String> updateEmail(@Valid @RequestBody EmailResetRequest request) throws BadRequestException {
        accountService.updateEmail(request);
        return RestResponse.success("email update success");
    }

    @PutMapping("/updateProfileName")
    public RestResponse<String> updateProfileName(@Valid @RequestBody ProfileNameResetRequest request) throws BadRequestException {
        accountService.updateProfileName(request);
        return RestResponse.success("profile name update success");
    }

}
