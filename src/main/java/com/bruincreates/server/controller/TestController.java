package com.bruincreates.server.controller;

import com.bruincreates.server.model.response.RestResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api")
public class TestController {

    @GetMapping("/test")
    @PreAuthorize("@ps.permission('user|admin')")
    public RestResponse<String> test() throws InterruptedException{
        return RestResponse.success();
    }

}
