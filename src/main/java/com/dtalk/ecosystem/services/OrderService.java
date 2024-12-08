package com.dtalk.ecosystem.services;

import com.dtalk.ecosystem.DTOs.request.order.UpdateOrderRequest;
import com.dtalk.ecosystem.entities.Order;

import java.util.List;

public interface OrderService {

    public Order createOrderByDesign(Long idBrand, Long idDesign);
    public Order createOrderByFolderStyle(Long idBrand, Long idFolderStyle);
    public Order affectDesign(Long idOrder , Long idDesign);
    public Order affectFolderStyle(Long idOrder , Long idFolderStyle);
    public Order desaffecterDesign(Long idOrder , Long idDesign);
    public Order desaffecterFolderStyle(Long idOrder , Long idFolderStyle);
    public Order getOrderById(long idOrder);
    public List<Order> getAllOrders();
    public List<Order> getOrdersByBrand(Long idBrand);
    public void deleteOrder(long idOrder);

    public Order updateQuantityAndAmount(Long idOrder, UpdateOrderRequest request);
}
