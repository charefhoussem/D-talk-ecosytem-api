package com.dtalk.ecosystem.controllers;

import com.dtalk.ecosystem.DTOs.request.livraison.LivraisonRequest;
import com.dtalk.ecosystem.entities.LivraisonProduction;
import com.dtalk.ecosystem.response.ResponseHandler;
import com.dtalk.ecosystem.services.LivraisonService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/livraison")
@AllArgsConstructor
public class LivraisonController {

    private final LivraisonService livraisonService;
    @PostMapping("/add/{idOrder}")
    public ResponseEntity<LivraisonProduction> saveLivraison(@PathVariable("idOrder") Long idOrder, @RequestBody LivraisonRequest request){
     return ResponseEntity.ok(livraisonService.createLivraison(idOrder,request));
    }

    @PostMapping("/update/{idOrder}")
    public ResponseEntity<LivraisonProduction> updateLivraison(@PathVariable("idOrder") Long idOrder, @RequestBody LivraisonRequest request) {
        return ResponseEntity.ok(livraisonService.updateLivraison(idOrder, request));
    }
    @GetMapping("/{idOrder}")
    public List<LivraisonProduction> getLivraisonsByOrder(@PathVariable("idOrder") Long idOrder){
        return livraisonService.getLivraisonsByOrder(idOrder);
    }

    @GetMapping("/by-id/{idLiv}")
    public LivraisonProduction getLivraison(@PathVariable("idLiv") Long id){
        return livraisonService.getLivraison(id);
    }

    @DeleteMapping("/delete/{idLivraison}")
    public ResponseEntity<Object> deleteLivraison(@PathVariable("idLivraison") Long id){
        livraisonService.deleteLivraison(id);
        return ResponseHandler.responseBuilder("livraison deleted", HttpStatus.OK, null);

    }


}
