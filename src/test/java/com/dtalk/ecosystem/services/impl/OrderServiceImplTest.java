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

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void testCreateOrderByDesign() {
        Long idBrand = 4L;
        Long idDesign = 1L;

        Brand brand = new Brand();
        brand.setIdUser(idBrand);

        Design design = new Design();
        design.setIdDesign(idDesign);

        when(brandRepository.findById(idBrand)).thenReturn(Optional.of(brand));
        when(designRepository.findById(idDesign)).thenReturn(Optional.of(design));

        Order order = orderService.createOrderByDesign(idBrand, idDesign);

        assertNotNull(order, "Order should not be null");
        assertEquals(brand, order.getBrand(), "Brand should be set in order");
        assertTrue(order.getDesigns().contains(design), "Design should be in order's designs set");
        verify(orderRepository).save(order);
    }

    @Test
    public void testCreateOrderByFolderStyle() {
        Long idBrand = 1L;
        Long idFolderStyle = 1L;

        Brand brand = new Brand();
        brand.setIdUser(idBrand);

        FolderStyle folderStyle = new FolderStyle();
        folderStyle.setIdFolder(idFolderStyle);

        when(brandRepository.findById(idBrand)).thenReturn(Optional.of(brand));
        when(folderStyleRepository.findById(idFolderStyle)).thenReturn(Optional.of(folderStyle));

        Order order = orderService.createOrderByFolderStyle(idBrand, idFolderStyle);

        assertNotNull(order);
        assertEquals(brand, order.getBrand());
        assertTrue(order.getFolderStyles().contains(folderStyle));
        assertTrue(order.getIsValid());
        verify(orderRepository).save(order);
        verify(prototypeRepository).save(any(Prototype.class));
    }

    @Test
    public void testAffectDesign() {
        Long idOrder = 1L;
        Long idDesign = 1L;

        Order order = new Order();
        order.setIdOrder(idOrder);

        Design design = new Design();
        design.setIdDesign(idDesign);

        when(orderRepository.findById(idOrder)).thenReturn(Optional.of(order));
        when(designRepository.findById(idDesign)).thenReturn(Optional.of(design));

        orderService.affectDesign(idOrder, idDesign);

        assertTrue(order.getDesigns().contains(design));
        verify(orderRepository).save(order);
    }

    @Test
    public void testAffectFolderStyle() {
        Long idOrder = 1L;
        Long idFolderStyle = 1L;

        Order order = new Order();
        order.setIdOrder(idOrder);

        FolderStyle folderStyle = new FolderStyle();
        folderStyle.setIdFolder(idFolderStyle);

        when(orderRepository.findById(idOrder)).thenReturn(Optional.of(order));
        when(folderStyleRepository.findById(idFolderStyle)).thenReturn(Optional.of(folderStyle));

        orderService.affectFolderStyle(idOrder, idFolderStyle);

        assertTrue(order.getFolderStyles().contains(folderStyle));
        verify(orderRepository).save(order);
    }

    @Test
    public void testDesaffecterDesign() {
        Long idOrder = 1L;
        Long idDesign = 1L;

        Order order = new Order();
        order.setIdOrder(idOrder);

        Design design = new Design();
        design.setIdDesign(idDesign);
        order.getDesigns().add(design);

        when(orderRepository.findById(idOrder)).thenReturn(Optional.of(order));
        when(designRepository.findById(idDesign)).thenReturn(Optional.of(design));

        orderService.desaffecterDesign(idOrder, idDesign);

        assertFalse(order.getDesigns().contains(design));
        verify(orderRepository).save(order);
    }

    @Test
    public void testDesaffecterFolderStyle() {
        Long idOrder = 1L;
        Long idFolderStyle = 1L;

        Order order = new Order();
        order.setIdOrder(idOrder);

        FolderStyle folderStyle = new FolderStyle();
        folderStyle.setIdFolder(idFolderStyle);
        order.getFolderStyles().add(folderStyle);

        when(orderRepository.findById(idOrder)).thenReturn(Optional.of(order));
        when(folderStyleRepository.findById(idFolderStyle)).thenReturn(Optional.of(folderStyle));

        orderService.desaffecterFolderStyle(idOrder, idFolderStyle);

        assertFalse(order.getFolderStyles().contains(folderStyle));
        verify(orderRepository).save(order);
    }

    @Test
    public void testGetOrderById() {
        Long idOrder = 1L;

        Order order = new Order();
        order.setIdOrder(idOrder);

        when(orderRepository.findById(idOrder)).thenReturn(Optional.of(order));

        Order result = orderService.getOrderById(idOrder);

        assertEquals(order, result);
    }

    @Test
    public void testGetAllOrders() {
        List<Order> orders = List.of(new Order(), new Order());

        when(orderRepository.findAll()).thenReturn(orders);

        List<Order> result = orderService.getAllOrders();

        assertEquals(orders, result);
    }

    @Test
    public void testDeleteOrder() {
        Long idOrder = 1L;

        orderService.deleteOrder(idOrder);

        verify(orderRepository).deleteById(idOrder);
    }


}
