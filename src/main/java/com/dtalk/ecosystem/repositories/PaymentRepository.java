package com.dtalk.ecosystem.repositories;

import com.dtalk.ecosystem.entities.Order;
import com.dtalk.ecosystem.entities.payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<payment,String> {
   List<payment> findPaiementsByOrderEquals(Order order);
}
