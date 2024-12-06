package com.dtalk.ecosystem.entities;

import com.dtalk.ecosystem.entities.enumiration.EtatReclamation;
import com.dtalk.ecosystem.entities.users.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name ="reclamations")

public class Reclamation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idReclamation;

    @NotBlank(message = "Description is mandatory")
    private String description;

    @NotNull(message = "Date is mandatory")
    private LocalDateTime date = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    private EtatReclamation etat = EtatReclamation.RECEIVED;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "design_id")
    private Design design;

    @ManyToOne
    @JoinColumn(name = "folderStyle_id")
    private FolderStyle folderStyle;

    @ManyToOne
    @JoinColumn(name = "prototype_id")
    private Prototype prototype;
}
