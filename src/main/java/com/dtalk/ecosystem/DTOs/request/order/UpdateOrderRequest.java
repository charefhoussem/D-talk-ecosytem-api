package com.dtalk.ecosystem.DTOs.request.order;

import lombok.Data;

@Data
public class UpdateOrderRequest {
    private int quantity;
    private Double amount;
}
