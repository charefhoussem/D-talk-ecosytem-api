package com.dtalk.ecosystem.services.impl;

import com.dtalk.ecosystem.DTOs.request.livraison.LivraisonRequest;
import com.dtalk.ecosystem.entities.LivraisonProduction;
import com.dtalk.ecosystem.entities.Order;
import com.dtalk.ecosystem.entities.enumiration.EtatOrder;
import com.dtalk.ecosystem.exceptions.ResourceInvalidException;
import com.dtalk.ecosystem.exceptions.ResourceNotFoundException;
import com.dtalk.ecosystem.repositories.LivraisonRepository;
import com.dtalk.ecosystem.repositories.OrderRepository;
import com.dtalk.ecosystem.services.LivraisonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LivraisonServiceImpl implements LivraisonService {
    private final OrderRepository orderRepository;
    private final LivraisonRepository livraisonRepository;
    @Override
    public LivraisonProduction createLivraison(Long idOrder, LivraisonRequest request) {
        Order order = orderRepository.findById(idOrder).orElseThrow(()-> new ResourceNotFoundException("order not found : "+ idOrder));
        if(order.getEtat() != EtatOrder.PRODUCTION){
            throw new ResourceInvalidException("cannot save livraison because order etat not production ");
        }
        LivraisonProduction livraisonProduction = LivraisonProduction.builder().date(request.getDate()).quantity(request.getQuantity()).order(order).build();
        return livraisonRepository.save(livraisonProduction);
    }

    @Override
    public LivraisonProduction updateLivraison(Long idLivraison,LivraisonRequest request) {
        LivraisonProduction livraisonProduction = livraisonRepository.findById(idLivraison).orElseThrow(()-> new ResourceNotFoundException("livraison not found : "+ idLivraison));
        livraisonProduction.setDate(request.getDate());
        livraisonProduction.setQuantity(request.getQuantity());

        return livraisonRepository.save(livraisonProduction);
    }

    @Override
    public LivraisonProduction getLivraison(Long idLivraison) {
        return livraisonRepository.findById(idLivraison).orElseThrow(()-> new ResourceNotFoundException("livraison not found : "+ idLivraison));
    }

    @Override
    public void deleteLivraison(Long idLivraison) {
        LivraisonProduction livraisonProduction = livraisonRepository.findById(idLivraison).orElseThrow(()-> new ResourceNotFoundException("livraison not found : "+ idLivraison));
       livraisonRepository.delete(livraisonProduction);

    }

    @Override
    public List<LivraisonProduction> getLivraisonsByOrder(Long idOrder) {
        Order order = orderRepository.findById(idOrder).orElseThrow(()-> new ResourceNotFoundException("order not found : "+ idOrder));
        return livraisonRepository.findLivraisonProductionsByOrderEquals(order);
    }
}
