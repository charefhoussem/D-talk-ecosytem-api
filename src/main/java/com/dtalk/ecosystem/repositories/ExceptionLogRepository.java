package com.dtalk.ecosystem.repositories;

import com.dtalk.ecosystem.entities.ExceptionLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExceptionLogRepository extends JpaRepository<ExceptionLog,Long> {
}
