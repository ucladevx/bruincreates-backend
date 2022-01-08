package com.bruincreates.server.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class UserController {

    @GetMapping
    @PreAuthorize(("@ps.permission('system:user:page')"))
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("success");
    }

}
