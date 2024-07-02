package com.dtalk.ecosystem.repositories;

import com.dtalk.ecosystem.entities.ProductionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductionTypeRepository extends JpaRepository<ProductionType,Long> {
}
