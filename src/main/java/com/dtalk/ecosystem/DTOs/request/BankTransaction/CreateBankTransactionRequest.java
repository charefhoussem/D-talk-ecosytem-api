package com.dtalk.ecosystem.DTOs.request.BankTransaction;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateBankTransactionRequest {
    @NotBlank(message = "Name is mandatory")
    @Size(max = 100, message = "Bank Name must be less than 100 characters")
    private String BankName;

    @Size(max = 255, message = "RIB must be less than 255 characters")
    private String rib;

    @NotBlank(message = "mode Payment  is mandatory")
    private String modePayment;


}
