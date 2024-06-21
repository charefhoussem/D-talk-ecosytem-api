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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
        return null;
    }
}
