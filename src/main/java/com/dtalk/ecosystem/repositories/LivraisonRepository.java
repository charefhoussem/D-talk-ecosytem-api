package com.dtalk.ecosystem.repositories;

import com.dtalk.ecosystem.entities.LivraisonProduction;
import com.dtalk.ecosystem.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LivraisonRepository extends JpaRepository<LivraisonProduction,Long> {
    List<LivraisonProduction> findLivraisonProductionsByOrderEquals(Order order);
}
