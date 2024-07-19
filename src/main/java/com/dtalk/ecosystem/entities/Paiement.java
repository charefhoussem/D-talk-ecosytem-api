package com.dtalk.ecosystem.entities;

import com.dtalk.ecosystem.entities.enumiration.ModePaiement;
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
    private String RefPaiement;

    private LocalDateTime date;

    private Boolean isCompleted = false;

    private Double amount = 0.0;
    @Enumerated(EnumType.STRING)
    private ModePaiement modePaiement = ModePaiement.KONNECT;

    @ManyToOne
    private Order order;

}
