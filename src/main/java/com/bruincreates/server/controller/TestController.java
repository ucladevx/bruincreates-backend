package com.bruincreates.server.controller;

import com.bruincreates.server.model.servlet.RestResponse;
import com.bruincreates.server.service.EmailService;
import com.bruincreates.server.utility.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/test")
public class TestController {

    @Autowired
    EmailService emailService;

    @GetMapping
    @PreAuthorize("@ps.permission('user|admin')")
    public RestResponse<String> test() {
        //emailService.sendSimpleEmail("ocestarsan@gmail.com", "Test", "Test Successes");
        /**
         * String jwt = JwtUtil.createToken("aojiao", "user");
         *         String username = JwtUtil.parseToken(jwt);
         *
         *         System.out.println("jwt granted: " + jwt);
         *         System.out.println("jwt deciphered: username=" + username);
         */
        return RestResponse.success();
    }

}
