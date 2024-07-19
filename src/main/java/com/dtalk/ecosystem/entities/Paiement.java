package com.dtalk.ecosystem.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "paiement")
public class Paiement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idPaiement;

    private LocalDateTime date;

    private Boolean isCompleted = false;

    private Double amount = 0.0;
}
