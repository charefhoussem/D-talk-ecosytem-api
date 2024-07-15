package com.dtalk.ecosystem.services.impl;

import com.dtalk.ecosystem.DTOs.request.folderStyle.AddFolderStyleRequest;
import com.dtalk.ecosystem.DTOs.request.folderStyle.ModifyFolderStyleRequest;
import com.dtalk.ecosystem.entities.*;
import com.dtalk.ecosystem.entities.users.FashionDesigner;
import com.dtalk.ecosystem.exceptions.ResourceNotFoundException;
import com.dtalk.ecosystem.repositories.FashionDesignerRepository;
import com.dtalk.ecosystem.repositories.FieldFolderStyleRepository;
import com.dtalk.ecosystem.repositories.FolderStyleRepository;
import com.dtalk.ecosystem.repositories.UserRepository;
import com.dtalk.ecosystem.services.EmailService;
import com.dtalk.ecosystem.services.FolderStyleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class FolderStyleServiceImpl implements FolderStyleService {

    private final UserRepository userRepository;
    private final FolderStyleRepository folderStyleRepository;
    private final FashionDesignerRepository fashionDesignerRepository;
    private final FieldFolderStyleRepository fieldFolderStyleRepository;
    private final FileStorageService fileStorageService;
    private final EmailService emailService;


    @Override
    public FolderStyle getFolderStyleById(Long idFolderStyle) {
        return folderStyleRepository.findById(idFolderStyle)
                .orElseThrow(() -> new ResourceNotFoundException("Folder style not found with id: " + idFolderStyle));
    }

    @Override
    public List<FolderStyle> retrieveAllFolderStyles() {
        return folderStyleRepository.findAll();
    }

    @Override
    public List<FolderStyle> retrieveAllFolderStyleAcceptedAndPublished() {
        return folderStyleRepository.findFolderStylesByIsPublishedIsTrueAndIsAcceptedIsTrue();
    }

    @Override
    public List<FolderStyle> retrieveAllFolderStyleByUser(Long idFashionDesigner) {
        FashionDesigner fashionD = fashionDesignerRepository.findById(idFashionDesigner)
                .orElseThrow(() -> new ResourceNotFoundException("Fashion designer not found with id: " + idFashionDesigner));
        return folderStyleRepository.findFolderStylesByFashionDesignerEquals(fashionD);
    }

    @Override
    public FolderStyle createFolderStyle(AddFolderStyleRequest request, Long idFashionDesigner) throws IOException {
        FashionDesigner fashionD = fashionDesignerRepository.findById(idFashionDesigner)
                .orElseThrow(() -> new ResourceNotFoundException("Fashion designer not found with id: " + idFashionDesigner));

        FolderStyle folder = new FolderStyle();
        folder.setName(request.getName());
        folder.setDescription(request.getDescription());
        folder.setType(request.getType());
        folder.setFile_3d_Path(request.getFile_3d_Path());
        folder.setPrice(request.getPrice());
        folder.setIsAccepted(false);
        folder.setIsPublished(false);

        String originFilePath = fileStorageService.saveFile(request.getOriginFile());
        folder.setOriginFile(originFilePath);

        Set<FieldFolderStyle> fieldFolderStyles = getOrCreateFields(request.getFields());
        folder.setFieldStyles(fieldFolderStyles);

        folder.setFashionDesigner(fashionD);
        return folderStyleRepository.save(folder);


    }


    @Override
    public Boolean acceptFolderStyle(Long idFolderStyle) {
        FolderStyle folder = folderStyleRepository.findById(idFolderStyle)
                .orElseThrow(() -> new ResourceNotFoundException("Folder not found with id: " + idFolderStyle));
        folder.setIsAccepted(true);
        folderStyleRepository.save(folder);
        emailService.notification(folder.getFashionDesigner().getEmail(),true,"NotificationFolderStyle","Notification Dossier de style");

        return true;
    }


    @Override
    public Boolean disacceptFolderStyle(Long idFolderStyle) {
        FolderStyle folder = folderStyleRepository.findById(idFolderStyle)
                .orElseThrow(() -> new ResourceNotFoundException("folder not found with id: " + idFolderStyle));
        folder.setIsAccepted(false);
        folderStyleRepository.save(folder);
        emailService.notification(folder.getFashionDesigner().getEmail(),false,"NotificationFolderStyle","Notification Dossier de style");

        return true;
    }

    @Override
    public Boolean publishFolderStyle(Long idFolderStyle) {
        FolderStyle folder = folderStyleRepository.findById(idFolderStyle)
                .orElseThrow(() -> new ResourceNotFoundException("folder not found with id: " + idFolderStyle));
        folder.setIsPublished(true);
        folderStyleRepository.save(folder);
        return true;
    }

    @Override
    public Boolean unpublishFolderStyle(Long idFolderStyle) {

        FolderStyle folder = folderStyleRepository.findById(idFolderStyle)
                .orElseThrow(() -> new ResourceNotFoundException("folder not found with id: " + idFolderStyle));
        folder.setIsPublished(false);
        folderStyleRepository.save(folder);
        return true;
    }

    @Override
    public FolderStyle modifyFolderStyle(ModifyFolderStyleRequest request) {
        FolderStyle folder = folderStyleRepository.findById(request.getId()).orElseThrow(()-> new ResourceNotFoundException("folder not found with id: " + request.getId()));
        folder.setName(request.getName());
        folder.setType(request.getType());
        folder.setPrice(request.getPrice());
        folder.setDescription(request.getDescription());

        Set<FieldFolderStyle> fieldFolderStyles = getOrCreateFields(request.getFields());
        folder.setFieldStyles(fieldFolderStyles);

        return folderStyleRepository.save(folder);

    }


    @Override
    public void deleteFolderStyle(Long idFolderStyle) {
        folderStyleRepository.findById(idFolderStyle).orElseThrow(()->new ResourceNotFoundException("Folder not found with id: " + idFolderStyle));
        folderStyleRepository.deleteById(idFolderStyle);
    }

    private Set<FieldFolderStyle> getOrCreateFields(List<String> FieldTitles) {
        Set<FieldFolderStyle> fields = new HashSet<>();
        for (String fieldTitle : FieldTitles) {
            FieldFolderStyle f = fieldFolderStyleRepository.findByTitle(fieldTitle)
                    .orElseGet(() -> FieldFolderStyle.builder().title(fieldTitle).build());
            fields.add(f);
        }
        return fields;
    }

}
