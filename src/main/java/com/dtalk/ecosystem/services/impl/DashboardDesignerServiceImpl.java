package com.dtalk.ecosystem.services.impl;

import com.dtalk.ecosystem.DTOs.response.dashboard_Designer.DesignerStatDTO;
import com.dtalk.ecosystem.DTOs.response.dashboard_Designer.MonthlyRevenueDTO;
import com.dtalk.ecosystem.DTOs.response.dashboard_Designer.TopSellingDesignDTO;
import com.dtalk.ecosystem.repositories.DesignRepository;
import com.dtalk.ecosystem.repositories.OrderRepository;
import com.dtalk.ecosystem.services.DashboardDesignerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor

public class DashboardDesignerServiceImpl implements DashboardDesignerService {
    @Autowired
    private final DesignRepository designRepository;

    @Autowired
    private final OrderRepository orderRepository;

    @Override
    public DesignerStatDTO getDesignerStats(Long idDesigner) {
        LocalDate now = LocalDate.now();
        LocalDate startOfMonth = now.withDayOfMonth(1);
        LocalDate sixMonthsAgo = now.minusMonths(6).withDayOfMonth(1);
        DesignerStatDTO stats = new DesignerStatDTO();

        // Gains et Nombre de ventes
        Double totalEarnings = orderRepository.getTotalEarningsForDesigner(idDesigner, startOfMonth, now);
        Long totalSales = orderRepository.getTotalSalesForDesigner(idDesigner, startOfMonth, now);
        stats.setTotalEarnings(totalEarnings);
        stats.setTotalSales(totalSales);

        // Revenus des 6 derniers mois
        List<MonthlyRevenueDTO> revenues = orderRepository.getMonthlyRevenuesForDesigner(idDesigner, sixMonthsAgo, now);
        stats.setMonthlyRevenues(revenues);

        // Top 5 des ventes
        List<TopSellingDesignDTO> topSellingDesigns = designRepository.getTopSellingDesigns(idDesigner, startOfMonth, now, 5);
        stats.setTopSellingDesigns(topSellingDesigns);

        // Évolution mensuelle
        Long salesLastMonth = orderRepository.getTotalSalesForDesigner(idDesigner, startOfMonth.minusMonths(1), startOfMonth.minusDays(1));
        Long salesThisMonth = totalSales; // Calculé précédemment
        stats.setMonthlySalesGrowth(calculateGrowth(salesLastMonth, salesThisMonth));

        // Designs approuvés et en attente
        Long approvedDesigns = designRepository.getApprovedDesignCount(idDesigner);
        Long pendingDesigns = designRepository.getPendingDesignCount(idDesigner);
        stats.setApprovedDesigns(approvedDesigns);
        stats.setPendingDesigns(pendingDesigns);
        return stats;
    }
    private Double calculateGrowth(Long previous, Long current) {
        if (previous == null || previous == 0) {
            return 100.0; // 100% croissance si pas de ventes précédentes
        }
        return ((current - previous) / (double) previous) * 100;
    }
}
