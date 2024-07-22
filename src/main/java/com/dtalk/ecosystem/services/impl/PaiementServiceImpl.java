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
    public Paiement createPaiement(String paiement_ref, Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(()-> new ResourceNotFoundException("order not found " + request.getIdOrder()));
        if( paiementRepository.findById(paiement_ref).isPresent()){
            throw new ResourceNotFoundException("ref paiement already exist");
        }
        var isCompleted = false;
        if(order.getPaiements().stream().mapToDouble(Paiement::getAmount).sum() + request.getAmount() == order.getAmount() ) {
          isCompleted = true;
        }
        Paiement paiement = Paiement.builder().refPaiement(request.getRefPaiement()).amount(request.getAmount()).date(request.getDate()).isCompleted(isCompleted).order(order).build();

        return paiementRepository.save(paiement);
    }

    @Override
    public List<Paiement> getPaiementByOrder(Long idOrder) {
        Order order = orderRepository.findById(idOrder).orElseThrow(()-> new ResourceNotFoundException("order not found " + idOrder));
        return paiementRepository.findPaiementsByOrderEquals(order);
    }

    @Override
    public List<Paiement> getAllPaiement() {
        return paiementRepository.findAll();
    }

    @Override
    public Paiement updatePaiement(String refPaiement, UpdatePaiementRequest request) {
        Paiement paiement = paiementRepository.findById(refPaiement).orElseThrow(()-> new ResourceNotFoundException("paiement not found"));
        paiement.setModePaiement(request.getModePaiement());
        paiement.setDate(request.getDate());
        paiement.setAmount(request.getAmount());
        return paiementRepository.save(paiement);
    }

    @Override
    public void deletePaiement(String refPaiement) {
        Paiement paiement = paiementRepository.findById(refPaiement).orElseThrow(()-> new ResourceNotFoundException("paiement not found"));
        paiementRepository.delete(paiement);

    }
}
