package com.dtalk.ecosystem.services;

import com.dtalk.ecosystem.DTOs.request.paiement.UpdatePaiementRequest;
import com.dtalk.ecosystem.entities.payment;

import java.util.List;

public interface PaymentService {
    public payment createPaiement(String paiement_ref, Long orderId);
    public List<payment> getPaiementByOrder(Long idOrder);
    public List<payment> getAllPaiement();

    public payment updatePaiement(String refPaiement , UpdatePaiementRequest request);

    public void deletePaiement(String refPaiement);

}
