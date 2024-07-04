package com.dtalk.ecosystem.controllers;

import com.dtalk.ecosystem.DTOs.request.folderStyle.AddFolderStyleRequest;
import com.dtalk.ecosystem.DTOs.request.folderStyle.ModifyFolderStyleRequest;
import com.dtalk.ecosystem.entities.FolderStyle;
import com.dtalk.ecosystem.response.ResponseHandler;
import com.dtalk.ecosystem.services.FolderStyleService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/folder-style")
@AllArgsConstructor
public class FolderStyleController {

    private final FolderStyleService folderStyleService;
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/")
   public List<FolderStyle> getALLFolderStyle(){
       return folderStyleService.retrieveAllFolderStyles();
   }
    @PreAuthorize("hasRole('FASHIONDESIGNER')")
    @GetMapping("/fashion-designer/{idUser}")
   public List<FolderStyle> getFoldersStyleByUser(@PathVariable("idUser") Long id){
     return folderStyleService.retrieveAllFolderStyleByUser(id);
   }

    @GetMapping("/{id}")
    public FolderStyle getFolderStyleById(@PathVariable Long id) {
        return folderStyleService.getFolderStyleById(id);
    }

    @GetMapping("/accepted-published")
    public List<FolderStyle> aaceptedAndPublishedFolder(){
        return folderStyleService.retrieveAllFolderStyleAcceptedAndPublished();

    }
    @PreAuthorize("hasRole('FASHIONDESIGNER')")
    @PostMapping("/add/{idFashionDesigner}")
    public ResponseEntity<Object> addFolderStyle(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("type") String type,
            @RequestParam("filePath") String filePath,
            @RequestParam("originFile") MultipartFile originFile,
            @RequestParam("price") Double price,
            @RequestParam("fields") List<String> fieldsStyle,

            @PathVariable("idFashionDesigner") Long idFashionDesigner
            ) throws IOException {
    AddFolderStyleRequest nf = new AddFolderStyleRequest();

    nf.setName(name);
    nf.setDescription(description);
    nf.setType(type);
    nf.setPrice(price);
    nf.setFile_3d_Path(filePath);
    nf.setOriginFile(originFile);
    nf.setFields(fieldsStyle);
    try {
        FolderStyle folder= folderStyleService.createFolderStyle(nf,idFashionDesigner);
       return ResponseHandler.responseBuilder("folder style added successfully", HttpStatus.OK, null);
      } catch (IOException e){
        return ResponseHandler.responseBuilder(e.getMessage(), HttpStatus.OK, null);
      }

    }
    @PreAuthorize("hasRole('FASHIONDESIGNER')")
    @PutMapping("/modify/{idFolderStyle}")
    public ResponseEntity<FolderStyle> modifyFolderStyle(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("type") String type,
            @RequestParam("price") Double price,
            @RequestParam("fields") List<String> fieldsStyle,
            @PathVariable("idFolderStyle") Long idFolderStyle

    ){

        ModifyFolderStyleRequest mf = new ModifyFolderStyleRequest();
        mf.setId(idFolderStyle);
        mf.setName(name);
        mf.setDescription(description);
        mf.setPrice(price);
        mf.setType(type);
        mf.setFields(fieldsStyle);

        FolderStyle modifyFolder = folderStyleService.modifyFolderStyle(mf);
        return ResponseEntity.ok(modifyFolder);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/accept/{id}")
    public ResponseEntity<Object> acceptFolder(@PathVariable("id") Long id){
        Boolean accept = folderStyleService.acceptFolderStyle(id);
        if (accept){
            return    ResponseHandler.responseBuilder("folder style accepted",HttpStatus.OK,null);
        }else{
            return    ResponseHandler.responseBuilder("folder style not found",HttpStatus.NOT_FOUND,null);
        }
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/disaccept/{id}")
    public ResponseEntity<Object> disacceptFolder(@PathVariable("id") Long id){
        Boolean disccept = folderStyleService.disacceptFolderStyle(id);
        if (disccept){
            return    ResponseHandler.responseBuilder("folder style disccepted",HttpStatus.OK,null);
        }else{
            return    ResponseHandler.responseBuilder("folder style not found",HttpStatus.NOT_FOUND,null);
        }
    }

    @PutMapping("/publish/{id}")
    public ResponseEntity<Object>  publichFolder(@PathVariable("id") Long id){
        Boolean ispublished = folderStyleService.publishFolderStyle(id);
        if (ispublished){
            return    ResponseHandler.responseBuilder("folder published",HttpStatus.OK,null);

        }else{
            return    ResponseHandler.responseBuilder("folder not found",HttpStatus.NOT_FOUND,null);

        }
    }

    @PutMapping("/unpublish/{id}")
    public ResponseEntity<Object>  unpublichFolder(@PathVariable("id") Long id){
        Boolean isunpublished = folderStyleService.unpublishFolderStyle(id);
        if (isunpublished){
            return    ResponseHandler.responseBuilder("folder unpublished",HttpStatus.OK,null);

        }else{
            return    ResponseHandler.responseBuilder("folder not found",HttpStatus.NOT_FOUND,null);

        }
    }
    @PreAuthorize("hasRole('FASHIONDESIGNER')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteFolder(@PathVariable("id") Long id){
       folderStyleService.deleteFolderStyle(id);
        return ResponseHandler.responseBuilder("design deleted",HttpStatus.OK,null);
    }



}
