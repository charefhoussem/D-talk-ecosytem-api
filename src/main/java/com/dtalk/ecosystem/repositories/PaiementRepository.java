package com.dtalk.ecosystem.repositories;

import com.dtalk.ecosystem.entities.Paiement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaiementRepository extends JpaRepository<Paiement,String> {

}
