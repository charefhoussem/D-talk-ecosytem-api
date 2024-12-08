package com.dtalk.ecosystem.repositories;

import com.dtalk.ecosystem.DTOs.request.design.DesignOrderSummaryProjection;
import com.dtalk.ecosystem.entities.Design;
import com.dtalk.ecosystem.entities.DesignStatus;
import com.dtalk.ecosystem.entities.users.Designer;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Repository
public interface DesignRepository extends JpaRepository<Design,Long> {

    List<Design> findDesignsByIsPublishedIsTrueAndIsAcceptedIsTrue();
    Page<Design> findDesignsByDesignerEquals(Designer designer, Pageable pageable);

    Page<Design> findDesignsByDesignerAndIsPublishedFalse(Designer designer, Pageable pageable);

    Page<Design> findDesignsByDesignerAndStatus(Designer designer, DesignStatus status,Pageable pageable);

    Page<Design> findDesignsByDesignerAndIsAcceptedFalseAndIsPublishedFalse(Designer designer,Pageable pageable);


    @Query(""" 
    SELECT d.name AS designName,
           d.imagePath AS imagePath,
           o.date AS orderDate,
           d.creationDate AS creationDate,
           COUNT(o) AS orderCount,
           SUM(o.quantity) AS totalQuantity,
           d.price AS priceDesign,
           d.price * totalQuantity AS totalPrice
    FROM Design d
    JOIN d.orders o
    WHERE d.designer.idUser = :idDesigner
    GROUP BY d.idDesign, d.name, d.imagePath, o.date
    ORDER BY o.date DESC
    """)
    Page<DesignOrderSummaryProjection> getGroupedOrdersByDesigner(@Param("idDesigner") Long idDesigner, Pageable pageable);

}
