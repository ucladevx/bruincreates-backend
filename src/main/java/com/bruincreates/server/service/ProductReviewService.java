package com.bruincreates.server.service;

import com.bruincreates.server.dao.mapper.ProductReviewMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductReviewService {

    @Autowired
    ProductReviewMapper productReviewMapper;

}
