package com.dtalk.ecosystem.controllers;

import com.dtalk.ecosystem.DTOs.request.paiement.UpdatePaiementRequest;
import com.dtalk.ecosystem.entities.Paiement;
import com.dtalk.ecosystem.services.PaiementService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/paiement")
@AllArgsConstructor
public class PaiementController {

    private final PaiementService paiementService;
    @PostMapping("/add")
    public ResponseEntity<Paiement> savePaiement(@RequestParam String paiement_ref, @RequestParam Long orderId){
        return ResponseEntity.ok(paiementService.createPaiement(paiement_ref,orderId));
    }
    @GetMapping("/")
    public List<Paiement> getAllPaiement(){
        return paiementService.getAllPaiement();
    }

    @GetMapping("/{idOrder}")
    public List<Paiement> getAllPaiementByOrder(@PathVariable("idOrder") Long idOrder){
        return paiementService.getPaiementByOrder(idOrder);
    }


    @PutMapping("/update/{refPaiement}")
    public ResponseEntity<Paiement> updatePaiement( @PathVariable("refPaiement") String ref , @RequestBody UpdatePaiementRequest request){
        return ResponseEntity.ok(paiementService.updatePaiement(ref,request));
    }

    @DeleteMapping("/delete/{refPaiement}")
    public ResponseEntity<Object> deletePaiement(@PathVariable("refPaiement") String ref){
        paiementService.deletePaiement(ref);
        return ResponseEntity.ok("paiement deleted successfuly");

    }


}
