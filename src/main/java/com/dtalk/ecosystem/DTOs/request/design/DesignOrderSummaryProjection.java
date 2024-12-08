package com.dtalk.ecosystem.DTOs.request.design;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DesignOrderSummaryProjection {
    private String designName;
    private String imagePath;
    private LocalDate orderDate;
    private LocalDate creationDate;
    private long orderCount;
    private long totalQuantity;
    private Double priceDesign;
    private double totalPrice;
}
