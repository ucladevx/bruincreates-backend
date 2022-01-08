package com.bruincreates.server.controller;

import com.bruincreates.server.model.servlet.RestResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController("/api/test")
public class TestController {

    @GetMapping
    @PreAuthorize("@ps.permission('system:user')")
    public RestResponse<String> test() {
        return RestResponse.success();
    }

}
