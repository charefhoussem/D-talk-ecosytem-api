package com.dtalk.ecosystem.repositories;

import com.dtalk.ecosystem.entities.FolderStyle;
import com.dtalk.ecosystem.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FolderStyleRepository extends JpaRepository<FolderStyle,Long> {
    List<FolderStyle> findFolderStylesByIsPublishedIsTrueAndIsAcceptedIsTrue();
    List <FolderStyle> findFolderStyleByUserEquals(User user);
}
