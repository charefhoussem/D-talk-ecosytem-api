package com.dtalk.ecosystem.DTOs.request.livraison;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class LivraisonRequest {
    private LocalDateTime date;
    private int quantity;
}
