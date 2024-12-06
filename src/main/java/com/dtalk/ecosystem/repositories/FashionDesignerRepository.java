package com.dtalk.ecosystem.repositories;

import com.dtalk.ecosystem.entities.users.FashionDesigner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FashionDesignerRepository extends JpaRepository<FashionDesigner,Long> {
}
