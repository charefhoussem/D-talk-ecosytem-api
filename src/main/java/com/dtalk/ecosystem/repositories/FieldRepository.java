package com.dtalk.ecosystem.repositories;

import com.dtalk.ecosystem.entities.FieldDesigner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FieldRepository extends JpaRepository<FieldDesigner,Long> {
    Optional<FieldDesigner> findByTitle(String title);
}
