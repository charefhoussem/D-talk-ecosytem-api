package com.dtalk.ecosystem.services.impl;

import com.dtalk.ecosystem.DTOs.request.design.DesignOrderSummaryProjection;
import com.dtalk.ecosystem.DTOs.request.design.RejectionReasonRequest;
import com.dtalk.ecosystem.DTOs.response.OrderedDesignDTO;
import com.dtalk.ecosystem.entities.Design;
import com.dtalk.ecosystem.entities.DesignStatus;
import com.dtalk.ecosystem.entities.FieldDesigner;
import com.dtalk.ecosystem.entities.Tag;
import com.dtalk.ecosystem.entities.users.Designer;
import com.dtalk.ecosystem.exceptions.ResourceNotFoundException;
import com.dtalk.ecosystem.repositories.*;
import com.dtalk.ecosystem.services.DesignService;
import com.dtalk.ecosystem.services.EmailService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import java.time.LocalDate;
import java.util.*;

@Service
@AllArgsConstructor
public class DesignServiceImpl implements DesignService {
    private final DesignRepository designRepository;
    private final TagRepository tagRepository;
    private final FieldRepository fieldRepository;
    private final EmailService emailService;
    private final DesignerRepository designerRepository;
    private FileStorageService fileStorageService;
    @Override
    public Design getDesignById(Long idDesign) {
        return designRepository.findById(idDesign)
                .orElseThrow(() -> new ResourceNotFoundException("Design not found with id: " + idDesign));    }

    @Override
    public List<Design> retrieveAllDesgins() {
        return designRepository.findAll();
    }

    @Override
    public List<Design> retrieveAllDesginsAcceptedAndPublished() {
      return designRepository.findDesignsByIsPublishedIsTrueAndIsAcceptedIsTrue();
     }

     @Override
     public Page<Design> retrieveAllDesginByUser(Long idDesigner ,int page) {
         Designer designer = designerRepository.findById(idDesigner)
                 .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + idDesigner));
         Pageable pageable = PageRequest.of(page,12);
         return designRepository.findDesignsByDesignerEquals(designer, pageable);
    }

    @Override
    public Page<Design> retrieveDesignsAcceptedByDesigner(Long idDesigner,int page) {
        Designer designer = designerRepository.findById(idDesigner)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + idDesigner));
        Pageable pageable = PageRequest.of(page,12);
        return designRepository.findDesignsByDesignerAndStatus(designer,DesignStatus.ACCEPTED,pageable);
    }

    @Override
    public Page<Design> retrieveDesignsNotAcceptedByDesigner(Long idDesigner , int page) {
        Designer designer = designerRepository.findById(idDesigner)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + idDesigner));
        Pageable pageable = PageRequest.of(page,12);

        return designRepository.findDesignsByDesignerAndStatus(designer,DesignStatus.REJECTED,pageable);
    }

    @Override
    public Page<Design> retrieveDesignsEnAttenteByDesigner(Long idDesigner,int page) {
        Designer designer = designerRepository.findById(idDesigner)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + idDesigner));
        Pageable pageable = PageRequest.of(page,12);

        return designRepository.findDesignsByDesignerAndStatus(designer,DesignStatus.ON_HOLD,pageable);
    }

    @Override
    public Page<Design> retrieveDesignsNotPublicByDesigner(Long idDesigner,int page) {
        Designer designer = designerRepository.findById(idDesigner)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + idDesigner));
        Pageable pageable = PageRequest.of(page,12);

        return designRepository.findDesignsByDesignerAndIsPublishedFalse(designer,pageable);
    }


    @Override
    public Design createDesign(String name, double price, String description, MultipartFile imageFile, MultipartFile originFile, Long idDesigner, List<String> tagNames,List<String> fieldTitles) throws IOException {
        Design design = new Design();
        design.setDescription(description);
        design.setName(name);
        design.setPrice(price);
        design.setIsAccepted(false);
        design.setIsPublished(false);
        LocalDate date = LocalDate.now();
        design.setCreationDate(date);
        Designer designer = designerRepository.findById(idDesigner).get();
        design.setDesigner(designer);
        String imageFileName = fileStorageService.saveFile(imageFile);
        design.setImagePath(imageFileName);

        String originFileName = fileStorageService.saveFile(originFile);
        design.setOriginFilePath(originFileName);

        Set<Tag> tags = getOrCreateTags(tagNames);
        design.setTags(tags);

        Set<FieldDesigner> fields = getOrCreateFields(fieldTitles);
        design.setFields(fields);
        return designRepository.save(design);
    }

    @Override
    public Boolean acceptDesign(Long idDesign) {
        Design design = designRepository.findById(idDesign)
            .orElseThrow(() -> new ResourceNotFoundException("Design not found with id: " + idDesign));
        design.setIsAccepted(true);
        design.setIsPublished(true);
        design.setStatus(DesignStatus.ACCEPTED);
        designRepository.save(design);
        emailService.notification(design.getDesigner().getEmail(),true,"NotificationDesignMail","Notification Design");

        return true;
    }

    @Override
    public Boolean rejectedDesign(Long idDesign, String reason) {
        Design design = designRepository.findById(idDesign)
                .orElseThrow(() -> new ResourceNotFoundException("Design not found with id: " + idDesign));
        design.setIsAccepted(false);
        design.setIsPublished(false);
        design.setRejection_reason(reason);
        design.setStatus(DesignStatus.REJECTED);

        designRepository.save(design);

        emailService.notification(design.getDesigner().getEmail(),false,"NotificationDesignMail","Notification Design");

        return true;
    }

    @Override
    public Boolean publishDesign(Long idDesign) {
        Design design = designRepository.findById(idDesign)
                .orElseThrow(() -> new ResourceNotFoundException("Design not found with id: " + idDesign));
        design.setIsPublished(true);
        designRepository.save(design);
        return true;
    }

    @Override
    public Boolean unpublishDesign(Long idDesign) {
        Design design = designRepository.findById(idDesign)
                .orElseThrow(() -> new ResourceNotFoundException("Design not found with id: " + idDesign));
        design.setIsPublished(false);
        designRepository.save(design);
        return true;
    }

    @Override
    public Design modifyDesign(Long id, String name, double price, String description,List<String> tagNames,List<String> fieldTitles) {
        Design design = designRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Design not found with id: " + id));
        design.setDescription(description);
        design.setName(name);
        design.setPrice(price);

        Set<Tag> tags = getOrCreateTags(tagNames);
        design.setTags(tags);


        Set<FieldDesigner> fields = getOrCreateFields(fieldTitles);
        design.setFields(fields);
        return designRepository.save(design);
    }



    @Override
    public void deleteDesign(Long idDesign) {
        Design design = designRepository.findById(idDesign).orElseThrow(()->new ResourceNotFoundException("Design not found with id: " + idDesign));
            designRepository.deleteById(idDesign);

    }

    @Override
    public Page<DesignOrderSummaryProjection> getOrderedDesignsByDesigner(Long idDesigner, int page, int size) {
         designerRepository.findById(idDesigner)
                 .orElseThrow(() -> new ResourceNotFoundException("designer not found with id: " + idDesigner));

        Pageable pageable = PageRequest.of(page,size);
        return designRepository.getGroupedOrdersByDesigner(idDesigner,pageable);


    }


    private Set<Tag> getOrCreateTags(List<String> tagNames) {
        Set<Tag> tags = new HashSet<>();
        for (String tagName : tagNames) {
            Tag tag = tagRepository.findByName(tagName)
                    .orElseGet(() -> Tag.builder().name(tagName).build());
            tags.add(tag);
        }
        return tags;
    }

    private Set<FieldDesigner> getOrCreateFields(List<String> FieldTitles) {
        Set<FieldDesigner> fields = new HashSet<>();
        for (String fieldTitle : FieldTitles) {
            FieldDesigner fild = fieldRepository.findByTitle(fieldTitle)
                    .orElseGet(() -> FieldDesigner.builder().title(fieldTitle).build());
            fields.add(fild);
        }
        return fields;
    }

}

