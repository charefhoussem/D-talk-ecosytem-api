package com.dtalk.ecosystem.repositories;

import com.dtalk.ecosystem.entities.FieldFolderStyle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FieldFolderStyleRepository extends JpaRepository<FieldFolderStyle,Long> {
}
