package com.dtalk.ecosystem.repositories;

import com.dtalk.ecosystem.entities.Design;
import com.dtalk.ecosystem.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DesignRepository extends JpaRepository<Design,Long> {

    List<Design> findDesignsByIsPublishedIsTrueAndIsAcceptedIsTrue();
    List<Design> findDesignsByUserEquals(User user);
}