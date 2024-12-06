package com.dtalk.ecosystem.services;

import com.dtalk.ecosystem.DTOs.request.livraison.LivraisonRequest;
import com.dtalk.ecosystem.entities.LivraisonProduction;

import java.util.List;

public interface LivraisonService {
    public LivraisonProduction createLivraison(Long idOrder, LivraisonRequest request);
    public LivraisonProduction updateLivraison(Long idLivraison,LivraisonRequest request);
    public LivraisonProduction getLivraison(Long idLivraison);

    public void deleteLivraison(Long idLivraison);
    public List<LivraisonProduction> getLivraisonsByOrder(Long idOrder);
}
