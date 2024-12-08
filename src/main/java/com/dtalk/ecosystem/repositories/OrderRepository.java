package com.dtalk.ecosystem.repositories;

import com.dtalk.ecosystem.DTOs.response.dashboard_Designer.MonthlyRevenueDTO;
import com.dtalk.ecosystem.entities.Order;
import com.dtalk.ecosystem.entities.users.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
    List<Order> getOrderByBrand(Brand brand);

    // dashboard designer

    //Gains : Les gains de tous les designs du designer.
    @Query("SELECT SUM(o.amount) FROM Order o JOIN o.designs d WHERE d.designer.idUser = :idDesigner AND o.date BETWEEN :start AND :end")
    Double getTotalEarningsForDesigner(@Param("idDesigner") Long idDesigner,
                                       @Param("start") LocalDate start,
                                       @Param("end") LocalDate end);

    //Nombre de ventes : Le total des ventes pour tous les designs.
    @Query("SELECT SUM(o.quantity) FROM Order o JOIN o.designs d WHERE d.designer.idUser = :idDesigner AND o.date BETWEEN :start AND :end")
    Long getTotalSalesForDesigner(@Param("idDesigner") Long idDesigner,
                                  @Param("start") LocalDate start,
                                  @Param("end") LocalDate end);

    @Query("""
    SELECT new com.dtalk.ecosystem.DTOs.response.dashboard_Designer.MonthlyRevenueDTO(FUNCTION('YEAR', o.date), FUNCTION('MONTH', o.date), SUM(o.amount))
    FROM Order o
    JOIN o.designs d
    WHERE d.designer.idUser = :idDesigner AND o.date BETWEEN :start AND :end
    GROUP BY FUNCTION('YEAR', o.date), FUNCTION('MONTH', o.date)
    ORDER BY FUNCTION('YEAR', o.date), FUNCTION('MONTH', o.date)
    """)
    List<MonthlyRevenueDTO> getMonthlyRevenuesForDesigner(@Param("idDesigner") Long idDesigner,
                                                          @Param("start") LocalDate start,
                                                          @Param("end") LocalDate end);

}
