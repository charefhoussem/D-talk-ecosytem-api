package com.dtalk.ecosystem.DTOs.request.livraison;

import lombok.Data;

import java.time.LocalDate;

@Data
public class LivraisonRequest {
    private LocalDate date;
    private int quantity;
}
