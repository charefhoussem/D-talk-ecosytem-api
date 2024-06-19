package com.dtalk.ecosystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExceptionLogRepository extends JpaRepository<ExceptionLogRepository,Long> {
}
