package com.dtalk.ecosystem.services;

import com.dtalk.ecosystem.DTOs.request.folderStyle.AddFolderStyleRequest;
import com.dtalk.ecosystem.DTOs.request.folderStyle.ModifyFolderStyleRequest;
import com.dtalk.ecosystem.entities.FolderStyle;

import java.io.IOException;
import java.util.List;

public interface FolderStyleService {
    public FolderStyle getFolderStyleById(Long idFolderStyle) ;
    public List<FolderStyle> retrieveAllFolderStyles();
    public List<FolderStyle> retrieveAllFolderStyleAcceptedAndPublished();

    public List<FolderStyle> retrieveAllFolderStyleByUser(Long idUser);
    public FolderStyle createFolderStyle(AddFolderStyleRequest request, Long idFashionDesigner) throws IOException;
    public Boolean acceptFolderStyle(Long idFolderStyle);
    public Boolean disacceptFolderStyle(Long idFolderStyle);

    public Boolean publishFolderStyle(Long idFolderStyle);
    public Boolean unpublishFolderStyle(Long idFolderStyle);

    public FolderStyle modifyFolderStyle(ModifyFolderStyleRequest request );

    public void deleteFolderStyle(Long idFolderStyle);

}
