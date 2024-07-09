package com.dtalk.ecosystem.repositories;

import com.dtalk.ecosystem.entities.Order;
import com.dtalk.ecosystem.entities.users.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
    List<Order> getOrderByBrand(Brand brand);
}
