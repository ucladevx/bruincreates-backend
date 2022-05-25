package com.bruincreates.server.service;

import com.bruincreates.server.dao.mapper.OrderMapper;
import com.bruincreates.server.dao.po.Order;
import com.bruincreates.server.dao.po.OrderExample;
import com.bruincreates.server.dao.po.Product;
import com.bruincreates.server.exception.BadRequestException;
import com.bruincreates.server.model.response.BuyerOrderResponse;
import com.bruincreates.server.model.response.SellerOrderResponse;
import com.bruincreates.server.utility.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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
    public Order cancelOrder(String username, String orderId) throws BadRequestException {
        OrderExample orderExample = new OrderExample();
        orderExample.createCriteria().andOrderIdEqualTo(orderId);

        Order order = orderMapper.selectByExample(orderExample).get(0);
        byte orderStatus = order.getOrderStatus();

        // under assumption that buyer id == buyer username
        // check if current user = buyer of the order
        if(!username.equals(order.getBuyerId()))
            throw new BadRequestException("current user does not match buyer of the order");
        // if order status is "shipped" or "completed", do not allow order cancellation
        else if (orderStatus == (byte) 3 || orderStatus == (byte) 4 || orderStatus == (byte) 5)
            throw new BadRequestException("order status is \"shipped\", \"completed\", or already \"cancelled\", order cancellation not allowed");

        order.setOrderStatus((byte) 5);
        order.setDeleted(true);

        orderMapper.updateByPrimaryKey(order);
        return order;
    }

    @Transactional
    public Order processOrder(String username, String orderId) throws BadRequestException{
        OrderExample orderExample = new OrderExample();
        orderExample.createCriteria().andOrderIdEqualTo(orderId);

        Order order = orderMapper.selectByExample(orderExample).get(0);
        byte status = order.getOrderStatus();

        if(username.equals(order.getBuyerId())){
            if(status==3) order.setOrderStatus((byte) 4);
            else throw new BadRequestException("action invalid");
        }
        else if (username.equals(order.getSellerId())){
            switch(status){
                case 1:
                    order.setOrderStatus((byte) 2);
                    break;
                case 2:
                    order.setOrderStatus((byte) 3);
                    break;
                default:
                    throw new BadRequestException("action invalid");
            }
        }
        else{
            throw new BadRequestException("current user is not involved in this order");
        }

        orderMapper.updateByPrimaryKey(order);
        return order;
    }

    public BuyerOrderResponse getBuyerHistory(String username) throws BadRequestException{
        BuyerOrderResponse response=new BuyerOrderResponse();
        OrderExample orderExample = new OrderExample();
        orderExample.createCriteria().andBuyerIdEqualTo(username);
        List<Order> results=orderMapper.selectByExample(orderExample);
        //get all orders with user as Buyer

        List<Order> pending=new ArrayList<Order>();
        List<Order> shipped=new ArrayList<Order>();
        List<Order> past=new ArrayList<Order>();
        List<Order> cancelled=new ArrayList<Order>();
        //the four lists in BuyerOrderResponse

        for(int i=0; i<results.size(); i++){
            Order temp=results.get(i);
            switch(temp.getOrderStatus()){
                case 2:
                    pending.add(temp);
                    break;
                case 3:
                    shipped.add(temp);
                case 4:
                    past.add(temp);
                    break;
                case 5:
                    cancelled.add(temp);
                    break;
            }
        }
        //adding different orders to corresponding lists.

        response.setPendingOrder(pending);
        response.setShippedOrder(shipped);
        response.setPastOrder(past);
        response.setCancelledOrder(cancelled);
        return response;
    }

    public SellerOrderResponse getSellerHistory(String username) throws BadRequestException{
        SellerOrderResponse response=new SellerOrderResponse();
        OrderExample orderExample = new OrderExample();
        orderExample.createCriteria().andSellerIdEqualTo(username);
        List<Order> results=orderMapper.selectByExample(orderExample);
        ////get all orders with user as Seller

        List<Order> pending=new ArrayList<Order>();
        List<Order> shipped=new ArrayList<Order>();
        List<Order> past=new ArrayList<Order>();
        List<Order> cancelled=new ArrayList<Order>();
        //the four lists in SellerOrderResponse

        for(int i=0; i<results.size(); i++){
            Order temp=results.get(i);
            switch(temp.getOrderStatus()){
                case 2:
                    pending.add(temp);
                    break;
                case 3:
                    shipped.add(temp);
                case 4:
                    past.add(temp);
                    break;
                case 5:
                    cancelled.add(temp);
                    break;
            }
        }
        //adding different orders to corresponding lists.

        response.setPendingOrder(pending);
        response.setShippedOrder(shipped);
        response.setPastOrder(past);
        response.setCancelledOrder(cancelled);
        return response;
    }

    public boolean hasCompletedOrder(String username, String productId) {

        OrderExample orderExample = new OrderExample();
        orderExample.createCriteria().andProductIdEqualTo(productId).andOrderStatusEqualTo((byte) 4).andBuyerIdEqualTo(username);
        List<Order> completedOrders = orderMapper.selectByExample(orderExample);

        return completedOrders.size() > 0;
    }
}
