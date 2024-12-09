package com.dtalk.ecosystem.DTOs.response.dashboard_Designer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DesignerStatDTO {
    private Double totalEarnings;
    private Long totalSales;
   // private List<CategorySalesDTO> categorySales;
    private List<MonthlyRevenueDTO> monthlyRevenues;
    private List<TopSellingDesignDTO> topSellingDesigns;
    private Double monthlySalesGrowth;
    private Long approvedDesigns;
    private Long pendingDesigns;
}
