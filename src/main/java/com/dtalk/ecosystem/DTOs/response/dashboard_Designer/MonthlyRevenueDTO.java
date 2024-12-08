package com.dtalk.ecosystem.DTOs.response.dashboard_Designer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MonthlyRevenueDTO {
    private Integer year;
    private Integer month;
    private Double revenue;
}
