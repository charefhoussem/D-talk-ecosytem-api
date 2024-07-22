package com.dtalk.ecosystem.services;

import com.dtalk.ecosystem.DTOs.request.paiement.AddPaiementRequest;
import com.dtalk.ecosystem.DTOs.request.paiement.UpdatePaiementRequest;
import com.dtalk.ecosystem.entities.Paiement;

import java.util.List;

public interface PaiementService {
    public Paiement createPaiement(String paiement_ref, Long orderId);
    public List<Paiement> getPaiementByOrder(Long idOrder);
    public List<Paiement> getAllPaiement();

    public Paiement updatePaiement(String refPaiement , UpdatePaiementRequest request);

    public void deletePaiement(String refPaiement);

}
