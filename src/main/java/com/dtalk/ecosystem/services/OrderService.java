package com.dtalk.ecosystem.services;

import com.dtalk.ecosystem.entities.Order;

import java.util.List;

public interface OrderService {

    Order createOrder(Order order);
    Order getOrderById(long idOrder);
    List<Order> getAllOrders();
    Order updateOrder(Order order);
    void deleteOrder(long idOrder);
}
