package com.dtalk.ecosystem.repositories;

import com.dtalk.ecosystem.entities.Design;
import com.dtalk.ecosystem.entities.FolderStyle;
import com.dtalk.ecosystem.entities.Prototype;
import com.dtalk.ecosystem.entities.Reclamation;
import com.dtalk.ecosystem.entities.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReclamationRepository extends JpaRepository<Reclamation,Long> {
    List<Reclamation> findReclamationsByUser(User user);
    List<Reclamation> findReclamationsByDesign(Design design);
    List<Reclamation> findReclamationsByPrototype(Prototype prototype);

    List<Reclamation> findReclamationsByFolderStyle(FolderStyle folderStyle);

}
