package com.bruincreates.server.service;

import com.bruincreates.server.dao.mapper.OrderMapper;
import com.bruincreates.server.dao.mapper.ProductReviewMapper;
import com.bruincreates.server.dao.po.Order;
import com.bruincreates.server.dao.po.OrderExample;
import com.bruincreates.server.dao.po.ProductReviewExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bruincreates.server.dao.po.ProductReview;
import com.bruincreates.server.exception.BadRequestException;

import java.util.List;

@Service
public class ProductReviewService {

    @Autowired
    ProductReviewMapper productReviewMapper;

    @Autowired
    OrderMapper orderMapper;

    public void addReview(String productId, String review, String username){
        //find the order with status = 4 (order completed)
        OrderExample orderExample = new OrderExample();
        orderExample.createCriteria()
                .andBuyerIdEqualsTo(username)
                .andProductIdEqualsTo(productId)
                .andOrderStatusEqualsTo((byte)4);
        List<Order> orderResultList = orderMapper.selectByExample(orderExample);

        //find the review for this buyer and this product
        ProductReviewExample productReviewExample = new ProductReviewExample();
        productReviewExample.createCriteria()
                .setBuyerIdEqualsTo(username)
                .setProductIdEqualsTo(productId)
                .setDeletedEqualsTo(false);
        List<ProductReview> productReviews = productReviewMapper.selectByExample(productReviewExample);

        //if the order is complete and there's no review yet, add one review for the product
        if (orderResultList.size() > 0 && productReviews.size() == 0) {
            ProductReview productReview = new ProductReview();
            productReview.setReviewer(username);
            productReview.setProductId(productId);
            productReview.setReview(review);
            productReviewMapper.insertSelective(productReview);
        }
    }

    public void deleteReview(ProductReview review){
        //check if the review exists and if the caller if the original buyer
        Integer id = review.getId();
        productReviewMapper.deleteByPrimaryKey(id);
    }

}
