package com.dtalk.ecosystem.repositories;

import com.dtalk.ecosystem.entities.Design;
import com.dtalk.ecosystem.entities.users.Designer;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
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

}
