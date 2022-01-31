package com.bruincreates.server.service;

import com.bruincreates.server.dao.mapper.OrderMapper;
import com.bruincreates.server.dao.po.Order;
import com.bruincreates.server.dao.po.Product;
import com.bruincreates.server.exception.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class OrderService {

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    ProductService productService;

    private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm:ss");

    @Transactional
    public Order createOrder(String buyer, String productId) throws BadRequestException {
        //secure product stock
        Product product = productService.checkInventoryAndBuy(productId);

        //construct order details
        Order order = new Order();
        order.setOrderId(productId + "-" + buyer+ "-" + product.getSellerId() + timeFormatter.format(LocalDateTime.now()));
        order.setProductId(product.getProductId());
        order.setSellerId(product.getSellerId());
        order.setBuyerId(buyer);
        order.setOrderType(product.getProductType());
        order.setOrderStatus((byte) 1);
        order.setTotalCharge(product.getProductPrice());

        //store order and return
        orderMapper.insertSelective(order);
        return order;
    }

}
