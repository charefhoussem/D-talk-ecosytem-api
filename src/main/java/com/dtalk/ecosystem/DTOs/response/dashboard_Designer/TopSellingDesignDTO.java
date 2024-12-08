package com.dtalk.ecosystem.DTOs.response.dashboard_Designer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopSellingDesignDTO {
    private String designName;
    private Long orderCount;
    private Long totalQuantity;
}
