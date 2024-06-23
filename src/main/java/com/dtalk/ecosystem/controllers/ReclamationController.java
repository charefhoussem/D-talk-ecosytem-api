package com.dtalk.ecosystem.controllers;

import com.dtalk.ecosystem.DTOs.request.ReclamationRequest;
import com.dtalk.ecosystem.entities.Reclamation;
import com.dtalk.ecosystem.response.ResponseHandler;
import com.dtalk.ecosystem.services.ReclamationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reclamation")
@AllArgsConstructor
public class ReclamationController {

    private final ReclamationService reclamationService;
   @PostMapping("/add/{idUser}/{idDesign}")
    public ResponseEntity<Reclamation> saveReclamation(@Valid @RequestBody ReclamationRequest request, @PathVariable("idUser") Long idUser,@PathVariable("idDesign") Long idDesign){
       return ResponseEntity.ok(reclamationService.addReclamation(request,idUser,idDesign));
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
    @DeleteMapping("/delete/{idRec}")
    public ResponseEntity<Object> deleteReclamation(@PathVariable("idRec") Long id){
       reclamationService.deleteReclamation(id);
        return ResponseHandler.responseBuilder("reclamation deleted", HttpStatus.OK, null);

    }


}
