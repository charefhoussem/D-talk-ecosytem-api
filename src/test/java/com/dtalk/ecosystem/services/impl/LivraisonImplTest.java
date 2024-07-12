package com.dtalk.ecosystem.services.impl;

import com.dtalk.ecosystem.DTOs.request.livraison.LivraisonRequest;
import com.dtalk.ecosystem.entities.LivraisonProduction;
import com.dtalk.ecosystem.entities.Order;
import com.dtalk.ecosystem.entities.enumiration.EtatOrder;
import com.dtalk.ecosystem.exceptions.ResourceInvalidException;
import com.dtalk.ecosystem.repositories.LivraisonRepository;
import com.dtalk.ecosystem.repositories.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class LivraisonImplTest {
    @Mock
    private OrderRepository orderRepository;

    @Mock
    private LivraisonRepository livraisonRepository;

    @InjectMocks
    private LivraisonServiceImpl livraisonService;

    private Order order;
    private LivraisonProduction livraison;
    private LivraisonRequest request;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        order = new Order();
        order.setIdOrder(1L);
        order.setEtat(EtatOrder.PRODUCTION);

        livraison = new LivraisonProduction();
        livraison.setIdLivraison(1L);
        livraison.setOrder(order);
        livraison.setDate(LocalDateTime.now());
        livraison.setQuantity(10);

        request = new LivraisonRequest();
        request.setDate(LocalDateTime.now());
        request.setQuantity(10);
    }

    @Test
    void testCreateLivraison() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(livraisonRepository.save(any(LivraisonProduction.class))).thenReturn(livraison);

        LivraisonProduction createdLivraison = livraisonService.createLivraison(1L, request);

        assertNotNull(createdLivraison);
        assertEquals(request.getDate(), createdLivraison.getDate());
        assertEquals(request.getQuantity(), createdLivraison.getQuantity());
        assertEquals(order, createdLivraison.getOrder());
        verify(livraisonRepository, times(1)).save(any(LivraisonProduction.class));
    }

    @Test
    void testCreateLivraisonWithInvalidOrderState() {
        order.setEtat(EtatOrder.PROTOTYPING);
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        ResourceInvalidException exception = assertThrows(ResourceInvalidException.class, () -> {
            livraisonService.createLivraison(1L, request);
        });

        assertEquals("cannot save livraison because order etat not production ", exception.getMessage());
        verify(livraisonRepository, never()).save(any(LivraisonProduction.class));
    }

    @Test
    void testUpdateLivraison() {
        when(livraisonRepository.findById(1L)).thenReturn(Optional.of(livraison));
        when(livraisonRepository.save(any(LivraisonProduction.class))).thenReturn(livraison);

        LivraisonProduction updatedLivraison = livraisonService.updateLivraison(1L, request);

        assertNotNull(updatedLivraison);
        assertEquals(request.getDate(), updatedLivraison.getDate());
        assertEquals(request.getQuantity(), updatedLivraison.getQuantity());
        verify(livraisonRepository, times(1)).save(any(LivraisonProduction.class));
    }

    @Test
    void testGetLivraison() {
        when(livraisonRepository.findById(1L)).thenReturn(Optional.of(livraison));

        LivraisonProduction foundLivraison = livraisonService.getLivraison(1L);

        assertNotNull(foundLivraison);
        assertEquals(livraison, foundLivraison);
        verify(livraisonRepository, times(1)).findById(1L);
    }

    @Test
    void testDeleteLivraison() {
        when(livraisonRepository.findById(1L)).thenReturn(Optional.of(livraison));

        livraisonService.deleteLivraison(1L);

        verify(livraisonRepository, times(1)).delete(livraison);
    }

    @Test
    void testGetLivraisonsByOrder() {
        List<LivraisonProduction> livraisons = Arrays.asList(livraison);
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(livraisonRepository.findLivraisonProductionsByOrderEquals(order)).thenReturn(livraisons);

        List<LivraisonProduction> foundLivraisons = livraisonService.getLivraisonsByOrder(1L);

        assertNotNull(foundLivraisons);
        assertEquals(livraisons, foundLivraisons);
        verify(livraisonRepository, times(1)).findLivraisonProductionsByOrderEquals(order);
    }
}
