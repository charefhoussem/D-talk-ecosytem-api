package com.dtalk.ecosystem.controllers;

import com.dtalk.ecosystem.DTOs.request.reclamation.ReclamationRequest;
import com.dtalk.ecosystem.entities.Reclamation;
import com.dtalk.ecosystem.response.ResponseHandler;
import com.dtalk.ecosystem.services.ReclamationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reclamation")
@AllArgsConstructor
public class ReclamationController {

    private final ReclamationService reclamationService;
   @PostMapping("/add/design/{idUser}/{idDesign}")
    public ResponseEntity<Reclamation> saveReclamationDesign(@Valid @RequestBody ReclamationRequest request, @PathVariable("idUser") Long idUser,@PathVariable("idDesign") Long idDesign){
       return ResponseEntity.ok(reclamationService.addReclamationDesign(request,idUser,idDesign));
    }

    @PostMapping("/add/folder/{idUser}/{idFolder}")
    public ResponseEntity<Reclamation> saveReclamationFolder(@Valid @RequestBody ReclamationRequest request, @PathVariable("idUser") Long idUser,@PathVariable("idFolder") Long idFolder){
        return ResponseEntity.ok(reclamationService.addReclamationFolderStyle(request,idUser,idFolder));
    }
    @PreAuthorize("hasRole('BRAND')")
    @PostMapping("/add/prototype/{idUser}/{idPrototype}")
    public ResponseEntity<Reclamation> saveReclamationPrototype(@Valid @RequestBody ReclamationRequest request, @PathVariable("idUser") Long idUser,@PathVariable("idPrototype") Long idPrototype){
        return ResponseEntity.ok(reclamationService.addReclamationPrototype(request,idUser,idPrototype));
    }

    @GetMapping("/{idReclamation}")
   public ResponseEntity<Reclamation> getReclamation(@PathVariable("idReclamation") Long id){
      return ResponseEntity.ok(reclamationService.getReclamationById(id));
    }
    @GetMapping("/")
    public List<Reclamation> getALLreclamation(){
       return reclamationService.getAllReclamation();
    }


    @GetMapping("/user/{idUser}")
    public List<Reclamation> getALLreclamationByUser(@PathVariable("idUser") Long id){
        return reclamationService.getAllReclamationByUser(id);
    }

    @GetMapping("/design/{idDesign}")
    public List<Reclamation> getALLreclamationByDesign(@PathVariable("idDesign") Long id){
        return reclamationService.getAllReclamationByDesign(id);
    }

    @GetMapping("/folder-style/{idFolder}")
    public List<Reclamation> getALLreclamationByFolder(@PathVariable("idFolder") Long id){
        return reclamationService.getAllReclamationByFolderStyle(id);
    }

    @GetMapping("/prototype/{idPrototype}")
    public List<Reclamation> getALLreclamationByPrototype(@PathVariable("idPrototype") Long id){
        return reclamationService.getAllReclamationByPrototype(id);
    }

    @DeleteMapping("/delete/{idRec}")
    public ResponseEntity<Object> deleteReclamation(@PathVariable("idRec") Long id){
       reclamationService.deleteReclamation(id);
        return ResponseHandler.responseBuilder("reclamation deleted", HttpStatus.OK, null);

    }


}
