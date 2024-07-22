package com.dtalk.ecosystem.DTOs.request.paiement;

import com.dtalk.ecosystem.entities.enumiration.ModePaiement;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UpdatePaiementRequest {

    private Double amount;
    private LocalDate date;
    private ModePaiement modePaiement;

}
