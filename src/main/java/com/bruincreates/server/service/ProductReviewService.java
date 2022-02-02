package com.bruincreates.server.service;

import com.bruincreates.server.dao.mapper.ProductReviewMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bruincreates.server.dao.po.ProductReview;
import com.bruincreates.server.exception.BadRequestException;

@Service
public class ProductReviewService {

    @Autowired
    ProductReviewMapper productReviewMapper;

    public void addReview(String username, Integer productId, String review){
        //check if the caller has purchased current product and if the current status is completed
        //create review only if not written one already
        ProductReview prodReview = new ProductReview();
        prodReview.setReviewer(username);
        prodReview.setId(productId);
        prodReview.setReview(review);
        productReviewMapper.insert(prodReview);
        
    }

    public void deleteReview(ProductReview review){
        //check if the review exists and if the caller if the original buyer
        Integer id = review.getId();
        productReviewMapper.deleteByPrimaryKey(id);
    }

}
