package com.dtalk.ecosystem.DTOs.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PaymentResponse {
    private Double amount ;
    private String status ;
    private LocalDate expirationDate;
}
