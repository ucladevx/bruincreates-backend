package com.bruincreates.server.controller;

import com.bruincreates.server.dao.po.Product;
import com.bruincreates.server.exception.BadRequestException;
import com.bruincreates.server.model.request.AccountUpdateRequest;
import com.bruincreates.server.model.request.PasswordResetRequest;
import com.bruincreates.server.model.request.PasswordResetUrlRequest;
import com.bruincreates.server.model.request.RegistrationRequest;
import com.bruincreates.server.model.response.RestResponse;
import com.bruincreates.server.model.user.UserControlBlock;
import com.bruincreates.server.service.AccountService;
import com.bruincreates.server.utility.JwtUtil;
import com.bruincreates.server.utility.UserUtil;
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

    @PutMapping("/updateAccount")
    public RestResponse<String> updateAccount(@Valid @RequestBody AccountUpdateRequest request) throws BadRequestException {
        String username = UserUtil.getRuntimeUser().getUsername();
        accountService.updateAccount(username, request);
        return RestResponse.success("account update success");
    }

}
