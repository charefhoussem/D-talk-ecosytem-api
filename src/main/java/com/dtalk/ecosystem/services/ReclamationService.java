package com.dtalk.ecosystem.services;

import com.dtalk.ecosystem.DTOs.request.ReclamationRequest;
import com.dtalk.ecosystem.entities.Reclamation;

public interface ReclamationService {
     public Reclamation addReclamation(ReclamationRequest request, Long idUser, Long IdDesign);

     public Reclamation getReclamationById(Long idRec);

}
