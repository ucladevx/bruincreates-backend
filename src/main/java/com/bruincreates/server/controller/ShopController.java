package com.bruincreates.server.controller;

import com.bruincreates.server.model.servlet.RestResponse;
import com.bruincreates.server.service.ShopService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/shop")
public class ShopController {

    @Autowired
    ShopService shopService;

    @GetMapping("/test")
    public RestResponse<String> shopTest() {
        return RestResponse.success();
    }

}
