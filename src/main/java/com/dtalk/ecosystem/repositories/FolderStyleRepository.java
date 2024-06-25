package com.dtalk.ecosystem.repositories;

import com.dtalk.ecosystem.entities.FolderStyle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FolderStyleRepository extends JpaRepository<FolderStyle,Long> {
}
