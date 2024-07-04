package com.dtalk.ecosystem.repositories;

import com.dtalk.ecosystem.entities.users.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin,Long> {
}
