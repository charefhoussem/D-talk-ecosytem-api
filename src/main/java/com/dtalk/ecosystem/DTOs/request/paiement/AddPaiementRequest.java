package com.dtalk.ecosystem.DTOs.request.paiement;

import java.time.LocalDateTime;

public class AddPaiementRequest {
    private String RefPaiement;
    private Long idOrder;
    private Double amount;
    private LocalDateTime date;
}
