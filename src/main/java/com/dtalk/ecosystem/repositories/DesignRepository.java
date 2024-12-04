package com.dtalk.ecosystem.repositories;

import com.dtalk.ecosystem.entities.Design;
import com.dtalk.ecosystem.entities.users.Designer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DesignRepository extends JpaRepository<Design,Long> {

    List<Design> findDesignsByIsPublishedIsTrueAndIsAcceptedIsTrue();
    List<Design> findDesignsByDesignerEquals(Designer designer);

    List<Design> findDesignsByDesignerAndIsPublishedFalse(Designer designer);

    List<Design> findDesignsByDesignerAndIsAcceptedFalse(Designer designer);

    List<Design> findDesignsByDesignerAndIsAcceptedTrue(Designer designer);

    List<Design> findDesignsByDesignerAndIsAcceptedFalseAndIsPublishedFalse(Designer designer);

}
