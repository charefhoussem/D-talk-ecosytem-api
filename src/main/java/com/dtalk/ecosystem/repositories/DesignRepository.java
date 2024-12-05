package com.dtalk.ecosystem.repositories;

import com.dtalk.ecosystem.entities.Design;
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

    Page<Design> findDesignsByDesignerAndIsAcceptedFalse(Designer designer,Pageable pageable);

    Page<Design> findDesignsByDesignerAndIsAcceptedTrue(Designer designer,Pageable pageable);

    Page<Design> findDesignsByDesignerAndIsAcceptedFalseAndIsPublishedFalse(Designer designer,Pageable pageable);

    @Query("SELECT d FROM Design d JOIN d.order o WHERE d.designer.idUser = :designerId")
    Page<Design> findOrderedDesignsByDesigner(@Param("designerId") Long designerId , Pageable pageable);

}
