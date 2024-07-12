package com.dtalk.ecosystem.services;

import com.dtalk.ecosystem.DTOs.request.reclamation.ReclamationRequest;
import com.dtalk.ecosystem.entities.Reclamation;

import java.util.List;

public interface ReclamationService {
     public Reclamation addReclamationDesign(ReclamationRequest request, Long idUser, Long IdDesign);
     public Reclamation addReclamationFolderStyle(ReclamationRequest request, Long idUser, Long IdFolder);
     public Reclamation addReclamationPrototype(ReclamationRequest request, Long idUser, Long IdPrototype);

     public Reclamation getReclamationById(Long idRec);

     public List<Reclamation> getAllReclamation();

     public List<Reclamation> getAllReclamationByUser(Long idUser);

     public List<Reclamation> getAllReclamationByDesign(Long idDesign);
     public List<Reclamation> getAllReclamationByFolderStyle(Long idFolder);
     public List<Reclamation> getAllReclamationByPrototype(Long idPrototype);


     public void deleteReclamation(Long idReclamation);



}
