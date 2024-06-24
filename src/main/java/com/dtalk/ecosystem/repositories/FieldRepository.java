package com.dtalk.ecosystem.repositories;

import com.dtalk.ecosystem.entities.Field;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FieldRepository extends JpaRepository<Field,Long> {
    Optional<Field> findByTitle(String title);
}
