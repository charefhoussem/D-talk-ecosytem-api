package com.dtalk.ecosystem.services.impl;

import com.dtalk.ecosystem.DTOs.request.reclamation.ReclamationRequest;
import com.dtalk.ecosystem.entities.Design;
import com.dtalk.ecosystem.entities.FolderStyle;
import com.dtalk.ecosystem.entities.Prototype;
import com.dtalk.ecosystem.entities.Reclamation;
import com.dtalk.ecosystem.entities.users.User;
import com.dtalk.ecosystem.exceptions.ResourceNotFoundException;
import com.dtalk.ecosystem.repositories.*;
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
    private final PrototypeRepository prototypeRepository;
    private final FolderStyleRepository folderStyleRepository;
    private final UserRepository userRepository;
    @Override
    public Reclamation addReclamationDesign(ReclamationRequest request, Long idUser, Long idDesign) {
        Design design = designRepository.findById(idDesign)
                .orElseThrow(() -> new ResourceNotFoundException("Design not found with id: " + idDesign));

        User user = userRepository.findById(idUser)
                .orElseThrow(() -> new ResourceNotFoundException("user not found with id: " + idUser));


        Reclamation reclamation = Reclamation.builder().description(request.getDescription()).user(user).design(design).build();
        return reclamationRepository.save(reclamation);
    }

    @Override
    public Reclamation addReclamationFolderStyle(ReclamationRequest request, Long idUser, Long IdFolder) {
        FolderStyle folder = folderStyleRepository.findById(IdFolder)
                .orElseThrow(() -> new ResourceNotFoundException("folder not found with id: " + IdFolder));

        User user = userRepository.findById(idUser)
                .orElseThrow(() -> new ResourceNotFoundException("user not found with id: " + idUser));


        Reclamation reclamation = Reclamation.builder().description(request.getDescription()).user(user).folderStyle(folder).build();
        return reclamationRepository.save(reclamation);
    }

    @Override
    public Reclamation addReclamationPrototype(ReclamationRequest request, Long idUser, Long IdPrototype) {
        Prototype prototype = prototypeRepository.findById(IdPrototype)
                .orElseThrow(() -> new ResourceNotFoundException("prototype not found with id: " + IdPrototype));

        User user = userRepository.findById(idUser)
                .orElseThrow(() -> new ResourceNotFoundException("Brand not found with id: " + idUser));


        Reclamation reclamation = Reclamation.builder().description(request.getDescription()).user(user).prototype(prototype).build();
        return reclamationRepository.save(reclamation);    }

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
    public List<Reclamation> getAllReclamationByFolderStyle(Long idFolder) {
        FolderStyle folderStyle = folderStyleRepository.findById(idFolder).orElseThrow(()->new ResourceNotFoundException("folder not found " + idFolder) );
        return reclamationRepository.findReclamationsByFolderStyle(folderStyle);
    }

    @Override
    public List<Reclamation> getAllReclamationByPrototype(Long idPrototype) {
        Prototype prototype = prototypeRepository.findById(idPrototype).orElseThrow(()->new ResourceNotFoundException("prototype not found " + idPrototype) );
        return reclamationRepository.findReclamationsByPrototype(prototype);    }

    @Override
    public void deleteReclamation(Long idReclamation) {
        Reclamation rec = reclamationRepository.findById(idReclamation).orElseThrow(()->new ResourceNotFoundException("reclamation not found " + idReclamation) );

        reclamationRepository.deleteById(idReclamation);

    }
}
