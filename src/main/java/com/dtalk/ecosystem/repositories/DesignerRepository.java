package com.dtalk.ecosystem.repositories;

import com.dtalk.ecosystem.entities.users.Designer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DesignerRepository extends JpaRepository<Designer,Long> {
}
