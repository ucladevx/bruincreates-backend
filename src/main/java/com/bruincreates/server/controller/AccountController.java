package com.bruincreates.server.controller;

import com.bruincreates.server.model.servlet.RestResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController("/api/account")
public class AccountController {

    @PostMapping
    public RestResponse<String> login() {
        return RestResponse.success();
    }

    @PostMapping
    public RestResponse<String> register() {
        return RestResponse.success();
    }

}
