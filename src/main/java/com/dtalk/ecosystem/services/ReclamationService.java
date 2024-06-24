package com.dtalk.ecosystem.services;

import com.dtalk.ecosystem.DTOs.request.ReclamationRequest;
import com.dtalk.ecosystem.entities.Reclamation;

import java.util.List;

public interface ReclamationService {
     public Reclamation addReclamation(ReclamationRequest request, Long idUser, Long IdDesign);

     public Reclamation getReclamationById(Long idRec);

     public List<Reclamation> getAllReclamation();

     public List<Reclamation> getAllReclamationByUser(Long idUser);

     public List<Reclamation> getAllReclamationByDesign(Long idDesign);

     public void deleteReclamation(Long idReclamation);



}
