package com.bruincreates.server.controller;

import com.bruincreates.server.model.response.RestResponse;
import com.bruincreates.server.service.ProductReviewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/review")
public class ProductReviewController {

    @Autowired
    ProductReviewService productReviewService;

    @GetMapping("/test")
    @PreAuthorize("@ps.permission('user|admin')")
    public RestResponse<String> test() {
        return RestResponse.success();
    }

    @GetMapping
    @PreAuthorize("@ps.permission('user|admin')")
    public RestResponse<String> getReviews(@RequestParam("product_id") String productId, @RequestParam("offset") int offset) {
        //TODO: implementation needed
        //TODO: 3 reviews are loaded everytime
        SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return RestResponse.success();
    }

}
