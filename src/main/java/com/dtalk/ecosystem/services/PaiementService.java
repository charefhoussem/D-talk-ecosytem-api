package com.dtalk.ecosystem.services;

import com.dtalk.ecosystem.entities.Paiement;

import java.util.List;

public interface PaiementService {
    public Paiement createPaiement();
    public List<Paiement> getPaiementByOrder(Long idOrder);
    public List<Paiement> getAllPaiement();


}
