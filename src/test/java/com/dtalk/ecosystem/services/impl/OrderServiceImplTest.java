package com.dtalk.ecosystem.services.impl;

import com.dtalk.ecosystem.entities.Design;
import com.dtalk.ecosystem.entities.FolderStyle;
import com.dtalk.ecosystem.entities.Order;
import com.dtalk.ecosystem.entities.Prototype;
import com.dtalk.ecosystem.entities.users.Brand;
import com.dtalk.ecosystem.repositories.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private BrandRepository brandRepository;

    @Mock
    private DesignRepository designRepository;

    @Mock
    private FolderStyleRepository folderStyleRepository;

    @Mock
    private PrototypeRepository prototypeRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    private Brand brand;
    private Design design;
    private FolderStyle folderStyle;
    private Order order;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        brand = new Brand();
        brand.setIdUser(1L);

        design = new Design();
        design.setIdDesign(1L);

        folderStyle = new FolderStyle();
        folderStyle.setIdFolder(1L);

        order = new Order();
        order.setIdOrder(1L);
        order.setBrand(brand);
        order.setDesigns(new HashSet<>());
        order.setFolderStyles(new HashSet<>());
    }

    @Test
    void testCreateOrderByDesign() {
        when(brandRepository.findById(1L)).thenReturn(Optional.of(brand));
        when(designRepository.findById(1L)).thenReturn(Optional.of(design));
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        Order createdOrder = orderService.createOrderByDesign(1L, 1L);

        assertNotNull(createdOrder);
        assertEquals(brand, createdOrder.getBrand());
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void testCreateOrderByFolderStyle() {
        when(brandRepository.findById(1L)).thenReturn(Optional.of(brand));
        when(folderStyleRepository.findById(1L)).thenReturn(Optional.of(folderStyle));
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        when(prototypeRepository.save(any(Prototype.class))).thenReturn(new Prototype());

        Order createdOrder = orderService.createOrderByFolderStyle(1L, 1L);

        assertNotNull(createdOrder);
        assertEquals(brand, createdOrder.getBrand());
        verify(orderRepository, times(1)).save(any(Order.class));
        verify(prototypeRepository, times(1)).save(any(Prototype.class));
    }

    @Test
    void testAffectDesign() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(designRepository.findById(1L)).thenReturn(Optional.of(design));
        when(designRepository.findById(2L)).thenReturn(Optional.of(new Design()));
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        Design newDesign = new Design();
        newDesign.setIdDesign(2L);

        Order updatedOrder = orderService.affectDesign(1L, 2L);

        assertNotNull(updatedOrder);
        assertEquals(1, updatedOrder.getDesigns().size());
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void testAffectFolderStyle() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(folderStyleRepository.findById(1L)).thenReturn(Optional.of(folderStyle));
        when(folderStyleRepository.findById(2L)).thenReturn(Optional.of(new FolderStyle()));
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        FolderStyle newFolderStyle = new FolderStyle();
        newFolderStyle.setIdFolder(2L);

        Order updatedOrder = orderService.affectFolderStyle(1L, 2L);

        assertNotNull(updatedOrder);
        assertEquals(1, updatedOrder.getFolderStyles().size());
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void testDesaffecterDesign() {
        order.getDesigns().add(design);
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(designRepository.findById(1L)).thenReturn(Optional.of(design));
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        Order updatedOrder = orderService.desaffecterDesign(1L, 1L);

        assertNotNull(updatedOrder);
        assertEquals(0, updatedOrder.getDesigns().size());
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void testDesaffecterFolderStyle() {
        order.getFolderStyles().add(folderStyle);
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(folderStyleRepository.findById(1L)).thenReturn(Optional.of(folderStyle));
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        Order updatedOrder = orderService.desaffecterFolderStyle(1L, 1L);

        assertNotNull(updatedOrder);
        assertEquals(0, updatedOrder.getFolderStyles().size());
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void testGetOrderById() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        Order foundOrder = orderService.getOrderById(1L);

        assertNotNull(foundOrder);
        assertEquals(order, foundOrder);
        verify(orderRepository, times(1)).findById(1L);
    }

    @Test
    void testGetAllOrders() {
        List<Order> orders = Arrays.asList(order);
        when(orderRepository.findAll()).thenReturn(orders);

        List<Order> foundOrders = orderService.getAllOrders();

        assertNotNull(foundOrders);
        assertEquals(orders, foundOrders);
        verify(orderRepository, times(1)).findAll();
    }

    @Test
    void testGetOrdersByBrand() {
        List<Order> orders = Arrays.asList(order);
        when(brandRepository.findById(1L)).thenReturn(Optional.of(brand));
        when(orderRepository.getOrderByBrand(brand)).thenReturn(orders);

        List<Order> foundOrders = orderService.getOrdersByBrand(1L);

        assertNotNull(foundOrders);
        assertEquals(orders, foundOrders);
        verify(orderRepository, times(1)).getOrderByBrand(brand);
    }

    @Test
    void testDeleteOrder() {
        orderService.deleteOrder(1L);

        verify(orderRepository, times(1)).deleteById(1L);
    }
}
