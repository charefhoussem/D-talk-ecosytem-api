package com.dtalk.ecosystem.services;

import com.dtalk.ecosystem.entities.Order;

import java.util.List;

public interface OrderService {

    public Order createOrderByDesign(Long idBrand, Long idDesign);
    public Order createOrderByFolderStyle(Long idBrand, Long idFolderStyle);
    public void affectDesign(Long idOrder , Long idDesign);
    public void affectFolderStyle(Long idOrder , Long idFolderStyle);
    public void desaffecterDesign(Long idOrder , Long idDesign);
    public void desaffecterFolderStyle(Long idOrder , Long idFolderStyle);
    public Order getOrderById(long idOrder);
    public List<Order> getAllOrders();
    public  Order updateOrder(Order order);
    public void deleteOrder(long idOrder);
}
