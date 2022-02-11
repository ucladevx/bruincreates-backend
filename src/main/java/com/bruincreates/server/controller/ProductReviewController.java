package com.bruincreates.server.controller;

import com.bruincreates.server.model.request.AddReviewRequest;
import com.bruincreates.server.model.response.RestResponse;
import com.bruincreates.server.model.user.UserControlBlock;
import com.bruincreates.server.service.ProductReviewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.bruincreates.server.utility.JwtUtil;
import org.springframework.security.core.context.SecurityContextHolder;

import java.security.Principal;
import com.bruincreates.server.dao.po.*;

import javax.validation.Valid;

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
        return RestResponse.success();
    }

    @PostMapping("/addReview")
    public RestResponse<String> addReview(@Valid @RequestBody AddReviewRequest request) {
        String username = UserUtil.getRuntimeUser.getUsername();
        productReviewService.addReview(request.getProductId(), request.getReview(), username);
        return RestResponse.success("Product Review Added");
    }

    @PostMapping("/deleteReview")
    public RestResponse<String> delteReview(ProductReview review) {
        productReviewService.deleteReview(review);
        return RestResponse.success("Product Review Deleted");
    }

}
