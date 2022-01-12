package com.bruincreates.server.service;

import com.bruincreates.server.dao.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    OrderMapper orderMapper;

}
