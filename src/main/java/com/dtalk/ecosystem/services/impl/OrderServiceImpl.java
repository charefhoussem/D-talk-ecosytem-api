package com.dtalk.ecosystem.services.impl;

import com.dtalk.ecosystem.entities.Order;
import com.dtalk.ecosystem.exceptions.ResourceNotFoundException;
import com.dtalk.ecosystem.repositories.OrderRepository;
import com.dtalk.ecosystem.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    @Override
    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public Order getOrderById(long idOrder) {
        return orderRepository.findById(idOrder).orElseThrow(()-> new ResourceNotFoundException("order not found :  " +idOrder));
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Order updateOrder(Order order) {
        return null;
    }

    @Override
    public void deleteOrder(long idOrder) {
        orderRepository.deleteById(idOrder);

    }
}
