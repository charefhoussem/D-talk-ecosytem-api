package com.dtalk.ecosystem.repositories;

import com.dtalk.ecosystem.entities.users.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository extends JpaRepository<Brand,Long> {
}
