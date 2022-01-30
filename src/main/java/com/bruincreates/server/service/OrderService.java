package com.bruincreates.server.service;

import com.bruincreates.server.dao.mapper.OrderMapper;
import com.bruincreates.server.dao.po.Order;
import com.bruincreates.server.dao.po.OrderExample;
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

    @Transactional
    public Order cancelOrder(String buyer, String orderId) throws BadRequestException {
        OrderExample orderExample = new OrderExample();
        orderExample.createCriteria().andOrderIdEqualTo(orderId);

        Order order = orderMapper.selectByExample(orderExample).get(0);

        // under assumption that buyer id == buyer username
        // check if current user = buyer of the order
        if(buyer.equals(order.getBuyerId()) == false)
            throw new BadRequestException("current user does not match buyer of the order");
        // if order status is "shipped" or "completed", do not allow order cancellation
        else if (order.getOrderStatus() == (byte) 3 || order.getOrderStatus() == (byte) 4)
            throw new BadRequestException("order status is \"shipped\" or \"completed\", order cancellation not allowed");

        order.setOrderStatus((byte) 5);
        order.setDeleted(true);

        orderMapper.updateByPrimaryKey(order);
        return order;
    }
}
