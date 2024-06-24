package com.dtalk.ecosystem.services.impl;

import com.dtalk.ecosystem.DTOs.request.ReclamationRequest;
import com.dtalk.ecosystem.entities.Design;
import com.dtalk.ecosystem.entities.Reclamation;
import com.dtalk.ecosystem.entities.User;
import com.dtalk.ecosystem.exceptions.ResourceNotFoundException;
import com.dtalk.ecosystem.repositories.DesignRepository;
import com.dtalk.ecosystem.repositories.ReclamationRepository;
import com.dtalk.ecosystem.repositories.UserRepository;
import com.dtalk.ecosystem.services.ReclamationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class ReclamationServiceImpl implements ReclamationService {
    private final ReclamationRepository reclamationRepository;
    private final DesignRepository designRepository;
    private final UserRepository userRepository;
    @Override
    public Reclamation addReclamation(ReclamationRequest request, Long idUser, Long idDesign) {
        Design design = designRepository.findById(idDesign)
                .orElseThrow(() -> new ResourceNotFoundException("Design not found with id: " + idDesign));

        User user = userRepository.findById(idUser)
                .orElseThrow(() -> new ResourceNotFoundException("user not found with id: " + idUser));

        LocalDateTime date = LocalDateTime.now();

        Reclamation reclamation = Reclamation.builder().description(request.getDescription()).etat("non traité").date(date).user(user).design(design).build();
        return reclamationRepository.save(reclamation);
    }

    @Override
    public Reclamation getReclamationById(Long idRec) {
        return reclamationRepository.findById(idRec).orElseThrow(()-> new ResourceNotFoundException("reclamation not found" + idRec));
    }

    @Override
    public List<Reclamation> getAllReclamation() {
        return reclamationRepository.findAll();
    }

    @Override
    public List<Reclamation> getAllReclamationByUser(Long idUser) {
        User user = userRepository.findById(idUser).orElseThrow(()->new ResourceNotFoundException("user not found " + idUser) );
        return reclamationRepository.findReclamationsByUser(user);
    }

    @Override
    public List<Reclamation> getAllReclamationByDesign(Long idDesign) {
        Design design = designRepository.findById(idDesign).orElseThrow(()->new ResourceNotFoundException("design not found " + idDesign) );
        return reclamationRepository.findReclamationsByDesign(design);
    }

    @Override
    public void deleteReclamation(Long idReclamation) {
        Reclamation rec = reclamationRepository.findById(idReclamation).orElseThrow(()->new ResourceNotFoundException("reclamation not found " + idReclamation) );

        reclamationRepository.deleteById(idReclamation);

    }
}
