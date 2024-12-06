package com.dtalk.ecosystem.repositories;

import com.dtalk.ecosystem.entities.Prototype;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrototypeRepository extends JpaRepository<Prototype,Long> {
}
