package com.dtalk.ecosystem.repositories;

import com.dtalk.ecosystem.entities.Design;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DesignRepository extends JpaRepository<Design,Long> {
}
