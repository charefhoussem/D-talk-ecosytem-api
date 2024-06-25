package com.dtalk.ecosystem.services;

import com.dtalk.ecosystem.entities.FolderStyle;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FolderStyleService {

    public FolderStyle getFolderStyleById(Long idFolder) ;
    public List<FolderStyle> retrieveAllFolderStyle();
    public List<FolderStyle> retrieveAllFolderStyleAcceptedAndPublished();

    public List<FolderStyle> retrieveAllFolderStyleByFashionDesigner(Long idUser);
    public FolderStyle createFolderStyle(String name, double price, String description, MultipartFile imageFile, MultipartFile originFile, Long idFashionDesigner, List<String> fieldTitles) throws IOException;
    public Boolean acceptFolderStyle(Long idFolder);
    public Boolean disacceptFolderStyle(Long idFolder);

    public Boolean publishFolderStyle(Long idFolder);
    public Boolean unpublishFolderStyle(Long idFolder);

    public FolderStyle modifyFolderStyle(Long id,String name, double price, String description,List<String> fieldTitles);

    public void deleteFolderStyle(Long idFolder);
}
