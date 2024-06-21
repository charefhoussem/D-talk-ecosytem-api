package com.dtalk.ecosystem.entities;

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
public class Reclamation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idReclamation;



    @NotBlank(message = "Description is mandatory")
    private String description;

    @NotNull(message = "Date is mandatory")
    private LocalDateTime date;

    @NotBlank(message = "etat is mandatory")
    private String etat;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "design_id", nullable = false)
    private Design design;
}
