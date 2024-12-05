package com.dtalk.ecosystem.DTOs.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderedDesignDTO {
    private String imagePath;
    private String designName;
    private double price;
    private int quantity;
    private double totalPrice;
    private Date orderDate;
    private LocalDate creationDate;
}
