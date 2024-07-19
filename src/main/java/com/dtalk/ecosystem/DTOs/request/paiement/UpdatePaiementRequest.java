package com.dtalk.ecosystem.DTOs.request.paiement;

import com.dtalk.ecosystem.entities.enumiration.ModePaiement;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UpdatePaiementRequest {

    private Double amount;
    private LocalDateTime date;
    private ModePaiement modePaiement;

}
