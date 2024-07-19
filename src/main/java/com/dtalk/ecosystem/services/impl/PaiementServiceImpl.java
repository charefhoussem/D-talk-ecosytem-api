package com.dtalk.ecosystem.services.impl;

import com.dtalk.ecosystem.DTOs.request.paiement.AddPaiementRequest;
import com.dtalk.ecosystem.DTOs.request.paiement.UpdatePaiementRequest;
import com.dtalk.ecosystem.entities.Order;
import com.dtalk.ecosystem.entities.Paiement;
import com.dtalk.ecosystem.exceptions.ResourceNotFoundException;
import com.dtalk.ecosystem.repositories.OrderRepository;
import com.dtalk.ecosystem.repositories.PaiementRepository;
import com.dtalk.ecosystem.services.PaiementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaiementServiceImpl implements PaiementService {

    private final PaiementRepository paiementRepository;
    private final OrderRepository orderRepository;


    @Override
    public Paiement createPaiement(AddPaiementRequest request) {
        Order order = orderRepository.findById(request.getIdOrder()).orElseThrow(()-> new ResourceNotFoundException("order not found " + request.getIdOrder()));
        if( paiementRepository.findById(request.getRefPaiement()).isPresent()){
            throw new ResourceNotFoundException("ref paiement already exist");
        }

        Paiement paiement = Paiement.builder().RefPaiement(request.getRefPaiement()).amount(request.getAmount()).date(request.getDate()).order(order).build();

        return paiementRepository.save(paiement);
    }

    @Override
    public List<Paiement> getPaiementByOrder(Long idOrder) {
        return null;
    }

    @Override
    public List<Paiement> getAllPaiement() {
        return null;
    }

    @Override
    public Paiement updatePaiement(String RefPaiement, UpdatePaiementRequest request) {
        return null;
    }

    @Override
    public void deletePaiement(String RefPaiement) {

    }
}
