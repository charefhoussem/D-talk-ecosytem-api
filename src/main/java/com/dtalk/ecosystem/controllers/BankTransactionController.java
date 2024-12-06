package com.dtalk.ecosystem.controllers;

import com.dtalk.ecosystem.DTOs.request.BankTransaction.CreateBankTransactionRequest;

import com.dtalk.ecosystem.entities.ModePayment;
import com.dtalk.ecosystem.response.ResponseHandler;
import com.dtalk.ecosystem.services.BankTransactionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/transaction")
@AllArgsConstructor
public class BankTransactionController {
    private final BankTransactionService bankTransactionService;

    @PreAuthorize("hasRole('DESIGNER')")
    @PostMapping("/add/{idDesigner}")
    public ResponseEntity<Object> addYtansaction(
            @RequestParam("bankName") String bankName,
            @RequestParam("rib") String rib,
            @RequestParam("modePayment") ModePayment modePayment,
            @PathVariable("idDesigner") Long idDesigner
    ) {
        CreateBankTransactionRequest newTrans = new CreateBankTransactionRequest();

        newTrans.setBankName(bankName);
        newTrans.setRib(rib);
        newTrans.setModePayment(String.valueOf(modePayment));

         bankTransactionService.createTransaction(newTrans,idDesigner);
        return ResponseHandler.responseBuilder("transaction added successfully", HttpStatus.OK, null);

    }

}
