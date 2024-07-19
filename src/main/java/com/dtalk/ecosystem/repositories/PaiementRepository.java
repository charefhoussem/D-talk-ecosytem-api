package com.dtalk.ecosystem.repositories;

import com.dtalk.ecosystem.entities.Order;
import com.dtalk.ecosystem.entities.Paiement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaiementRepository extends JpaRepository<Paiement,String> {
   List<Paiement> findPaiementsByOrderEquals(Order order);
}
