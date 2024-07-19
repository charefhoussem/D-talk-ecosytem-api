package com.dtalk.ecosystem.services.impl;

import com.dtalk.ecosystem.DTOs.request.paiement.AddPaiementRequest;
import com.dtalk.ecosystem.DTOs.request.paiement.UpdatePaiementRequest;
import com.dtalk.ecosystem.entities.Paiement;
import com.dtalk.ecosystem.services.PaiementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaiementServiceImpl implements PaiementService {


    @Override
    public Paiement createPaiement(AddPaiementRequest request) {

        return null;
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
