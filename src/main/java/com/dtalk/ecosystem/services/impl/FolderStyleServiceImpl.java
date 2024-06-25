package com.dtalk.ecosystem.services.impl;

import com.dtalk.ecosystem.entities.FolderStyle;
import com.dtalk.ecosystem.services.FolderStyleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
@Service
@AllArgsConstructor
public class FolderStyleServiceImpl implements FolderStyleService {
    @Override
    public FolderStyle getFolderStyleById(Long idFolder) {
        return null;
    }

    @Override
    public List<FolderStyle> retrieveAllFolderStyle() {
        return null;
    }

    @Override
    public List<FolderStyle> retrieveAllFolderStyleAcceptedAndPublished() {
        return null;
    }

    @Override
    public List<FolderStyle> retrieveAllFolderStyleByFashionDesigner(Long idUser) {
        return null;
    }

    @Override
    public FolderStyle createFolderStyle(String name, double price, String description, MultipartFile imageFile, MultipartFile originFile, Long idFashionDesigner, List<String> fieldTitles) throws IOException {
        return null;
    }

    @Override
    public Boolean acceptFolderStyle(Long idFolder) {
        return null;
    }

    @Override
    public Boolean disacceptFolderStyle(Long idFolder) {
        return null;
    }

    @Override
    public Boolean publishFolderStyle(Long idFolder) {
        return null;
    }

    @Override
    public Boolean unpublishFolderStyle(Long idFolder) {
        return null;
    }

    @Override
    public FolderStyle modifyFolderStyle(Long id, String name, double price, String description, List<String> fieldTitles) {
        return null;
    }

    @Override
    public void deleteFolderStyle(Long idFolder) {

    }
}
