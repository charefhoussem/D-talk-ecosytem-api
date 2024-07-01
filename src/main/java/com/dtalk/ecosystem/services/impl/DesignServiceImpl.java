package com.dtalk.ecosystem.services.impl;

import com.dtalk.ecosystem.entities.Design;
import com.dtalk.ecosystem.entities.Field;
import com.dtalk.ecosystem.entities.Tag;
import com.dtalk.ecosystem.entities.User;
import com.dtalk.ecosystem.exceptions.ResourceNotFoundException;
import com.dtalk.ecosystem.repositories.DesignRepository;
import com.dtalk.ecosystem.repositories.FieldRepository;
import com.dtalk.ecosystem.repositories.TagRepository;
import com.dtalk.ecosystem.repositories.UserRepository;
import com.dtalk.ecosystem.services.DesignService;
import com.dtalk.ecosystem.services.EmailService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import java.util.*;

@Service
@AllArgsConstructor
public class DesignServiceImpl implements DesignService {
    private final DesignRepository designRepository;
    private final UserRepository userRepository;
    private final TagRepository tagRepository;
    private final FieldRepository fieldRepository;
    private final EmailService emailService;

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
     public List<Design> retrieveAllDesginByUser(Long idUser) {
         User user = userRepository.findById(idUser)
                 .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + idUser));
         return designRepository.findDesignsByUserEquals(user);
    }


    @Override
    public Design createDesign(String name, double price, String description, MultipartFile imageFile, MultipartFile originFile, Long idDesigner, List<String> tagNames,List<String> fieldTitles) throws IOException {
        Design design = new Design();
        design.setDescription(description);
        design.setName(name);
        design.setPrice(price);
        design.setIsAccepted(false);
        design.setIsPublished(false);
        User user = userRepository.findById(idDesigner).get();
        design.setUser(user);
        String imageFileName = fileStorageService.saveFile(imageFile);
        design.setImagePath(imageFileName);

        String originFileName = fileStorageService.saveFile(originFile);
        design.setOriginFilePath(originFileName);

        Set<Tag> tags = getOrCreateTags(tagNames);
        design.setTags(tags);

        Set<Field> fields = getOrCreateFields(fieldTitles);
        design.setFields(fields);
        return designRepository.save(design);
    }

    @Override
    public Boolean acceptDesign(Long idDesign) {
        Design design = designRepository.findById(idDesign)
            .orElseThrow(() -> new ResourceNotFoundException("Design not found with id: " + idDesign));
        design.setIsAccepted(true);
        designRepository.save(design);
        emailService.notification(design.getUser().getEmail(),true,"NotificationDesignMail","Notification Design");

        return true;
    }

    @Override
    public Boolean disacceptDesign(Long idDesign) {
        Design design = designRepository.findById(idDesign)
                .orElseThrow(() -> new ResourceNotFoundException("Design not found with id: " + idDesign));
        design.setIsAccepted(false);
        designRepository.save(design);

        emailService.notification(design.getUser().getEmail(),false,"NotificationDesignMail","Notification Design");

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


        Set<Field> fields = getOrCreateFields(fieldTitles);
        design.setFields(fields);
        return designRepository.save(design);
    }



    @Override
    public void deleteDesign(Long idDesign) {
        Design design = designRepository.findById(idDesign).orElseThrow(()->new ResourceNotFoundException("Design not found with id: " + idDesign));
            designRepository.deleteById(idDesign);

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

    private Set<Field> getOrCreateFields(List<String> FieldTitles) {
        Set<Field> fields = new HashSet<>();
        for (String fieldTitle : FieldTitles) {
            Field fild = fieldRepository.findByTitle(fieldTitle)
                    .orElseGet(() -> Field.builder().title(fieldTitle).build());
            fields.add(fild);
        }
        return fields;
    }

}

