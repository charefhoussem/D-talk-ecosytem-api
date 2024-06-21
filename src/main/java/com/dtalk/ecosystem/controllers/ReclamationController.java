package com.dtalk.ecosystem.controllers;

import com.dtalk.ecosystem.DTOs.request.ReclamationRequest;
import com.dtalk.ecosystem.entities.Reclamation;
import com.dtalk.ecosystem.services.ReclamationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reclamation")
@AllArgsConstructor
public class ReclamationController {

    private final ReclamationService reclamationService;
@PostMapping("/add/{idUser}/{idDesign}")
    public ResponseEntity<Reclamation> saveReclamation(@Valid @RequestBody ReclamationRequest request, @PathVariable("idUser") Long idUser,@PathVariable("idDesign") Long idDesign){
       return ResponseEntity.ok(reclamationService.addReclamation(request,idUser,idDesign));
    }


}
