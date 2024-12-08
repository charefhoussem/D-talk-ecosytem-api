package com.dtalk.ecosystem.repositories;

import com.dtalk.ecosystem.DTOs.request.design.DesignOrderSummaryProjection;
import com.dtalk.ecosystem.DTOs.response.dashboard_Designer.TopSellingDesignDTO;
import com.dtalk.ecosystem.entities.Design;
import com.dtalk.ecosystem.entities.DesignStatus;
import com.dtalk.ecosystem.entities.users.Designer;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DesignRepository extends JpaRepository<Design,Long> {

    List<Design> findDesignsByIsPublishedIsTrueAndIsAcceptedIsTrue();
    Page<Design> findDesignsByDesignerEquals(Designer designer, Pageable pageable);

    Page<Design> findDesignsByDesignerAndIsPublishedFalse(Designer designer, Pageable pageable);

    Page<Design> findDesignsByDesignerAndStatus(Designer designer, DesignStatus status,Pageable pageable);

    Page<Design> findDesignsByDesignerAndIsAcceptedFalseAndIsPublishedFalse(Designer designer,Pageable pageable);

    @Query("""
    SELECT new com.dtalk.ecosystem.DTOs.request.design.DesignOrderSummaryProjection( d.name,
           d.imagePath,
           o.date,
           d.creationDate,
           COUNT(o),
           SUM(o.quantity),
           d.price,
           d.price * SUM(o.quantity))
    FROM Design d
    JOIN d.orders o
    WHERE d.designer.idUser = :idDesigner
    GROUP BY d.idDesign, d.name, d.imagePath, o.date
    ORDER BY o.date DESC
    """)
    Page<DesignOrderSummaryProjection> getGroupedOrdersByDesigner(@Param("idDesigner") Long idDesigner, Pageable pageable);


    // dashboard designer

    //Designs approuvés : Le nombre total de designs approuvés.
    @Query("SELECT COUNT(d) FROM Design d WHERE d.designer.idUser = :idDesigner AND d.status = 'ACCEPTED'")
    Long getApprovedDesignCount(@Param("idDesigner") Long idDesigner);

    //Designs en attente : Le nombre de designs en attente de validation.
    @Query("SELECT COUNT(d) FROM Design d WHERE d.designer.idUser = :idDesigner AND d.status = 'ON_HOLD'")
    Long getPendingDesignCount(@Param("idDesigner") Long idDesigner);

    //Top 5 des ventes : Les 5 designs les plus vendus
    @Query("""
    SELECT new com.dtalk.ecosystem.DTOs.response.dashboard_Designer.TopSellingDesignDTO(d.name, COUNT(o), SUM(o.quantity))
    FROM Design d
    JOIN d.orders o
    WHERE d.designer.idUser = :idDesigner AND o.date BETWEEN :start AND :end
    GROUP BY d.idDesign, d.name
    ORDER BY SUM(o.quantity) DESC
    LIMIT :limit
    """)
    List<TopSellingDesignDTO> getTopSellingDesigns(@Param("idDesigner") Long idDesigner,
                                                   @Param("start") LocalDate start,
                                                   @Param("end") LocalDate end,
                                                   @Param("limit") int limit);





}
