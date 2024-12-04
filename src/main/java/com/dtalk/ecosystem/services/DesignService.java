package com.dtalk.ecosystem.services;

import com.dtalk.ecosystem.entities.Design;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface DesignService {
    public Design getDesignById(Long idDesign) ;
    public List<Design> retrieveAllDesgins();
    public List<Design> retrieveAllDesginsAcceptedAndPublished();

    public List<Design> retrieveAllDesginByUser(Long idUser);

    public List<Design> retrieveDesignsAcceptedByDesigner(Long idDesigner);

    public List<Design> retrieveDesignsNotAcceptedByDesigner(Long idDesigner);


    public List<Design> retrieveDesignsEnAttenteByDesigner(Long idDesigner);


    public List<Design> retrieveDesignsNotPublicByDesigner(Long idDesigner);




    public Design createDesign(String name, double price,String description, MultipartFile imageFile, MultipartFile originFile,Long idDesigner,List<String> tagNames,List<String> fieldTitles) throws IOException;
    public Boolean acceptDesign(Long idDesign);
    public Boolean disacceptDesign(Long idDesign);

    public Boolean publishDesign(Long idDesign);
    public Boolean unpublishDesign(Long idDesign);

    public Design modifyDesign(Long id,String name, double price, String description,List<String> tagNames,List<String> fieldTitles);

    public void deleteDesign(Long idDesign);
}
