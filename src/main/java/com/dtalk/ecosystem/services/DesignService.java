package com.dtalk.ecosystem.services;

import com.dtalk.ecosystem.entities.Design;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface DesignService {
    public Design getDesignById(Long idDesign) ;
    public List<Design> retrieveAllDesgins();
    public List<Design> retrieveAllDesginsAcceptedAndPublished();

    public List<Design> retrieveAllDesginByUser(Long idUser);
    public Design createDesign(String name, double price,String description, MultipartFile imageFile, MultipartFile originFile,Long idDesigner,List<String> tagNames,String field) throws IOException;
    public Boolean acceptDesign(Long idDesign);
    public Boolean disacceptDesign(Long idDesign);

    public Boolean publishDesign(Long idDesign);
    public Boolean unpublishDesign(Long idDesign);

    public Design modifyDesign(Long id,String name, double price, String description);

    public void deleteDesign(Long idDesign);
}
