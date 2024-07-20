package com.dtalk.ecosystem.DTOs.request.paiement;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class AddPaiementRequest {
    private String refPaiement;
    private Long idOrder;
    private Double amount;
    private LocalDateTime date;
}
