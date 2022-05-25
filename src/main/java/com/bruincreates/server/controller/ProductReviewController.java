package com.bruincreates.server.controller;

import com.bruincreates.server.exception.BadRequestException;
import com.bruincreates.server.model.request.DeleteReviewRequest;
import com.bruincreates.server.model.response.RestResponse;
import com.bruincreates.server.model.request.CreateReviewRequest;
import com.bruincreates.server.service.ProductReviewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/review")
public class ProductReviewController {

    @Autowired
    ProductReviewService productReviewService;

    @PostMapping("/create")
    @PreAuthorize("@ps.permission('user|admin')")
    public RestResponse<String> createReviews(@Valid @RequestBody CreateReviewRequest request) throws BadRequestException {
        productReviewService.createReview(request);

        return RestResponse.success("Review posted");
    }

    @PostMapping("/delete")
    public RestResponse<String> deleteReview(@Valid @RequestBody DeleteReviewRequest request) throws BadRequestException {
        productReviewService.deleteReview(request);

        return RestResponse.success("Review deleted");
    }

    @GetMapping("/get")
    @PreAuthorize("@ps.permission('user|admin')")
    public RestResponse<String> getReviews(@RequestParam("product_id") String productId, @RequestParam("offset") int offset) {
        // TODO: implementation needed
        // TODO: offset = 3 means 3 reviews are loaded everytime
        // TODO: return a list of reviews
        SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return RestResponse.success();
    }

}
