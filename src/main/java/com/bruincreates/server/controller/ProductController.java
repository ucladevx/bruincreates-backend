package com.bruincreates.server.controller;

import com.bruincreates.server.model.response.RestResponse;
import com.bruincreates.server.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping("/test")
    public RestResponse<String> test() {
        return RestResponse.success();
    }

}
