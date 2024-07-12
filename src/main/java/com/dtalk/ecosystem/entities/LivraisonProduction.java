package com.dtalk.ecosystem.entities;

import jakarta.persistence.*;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name ="livraison_production")
public class LivraisonProduction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idLivraison;

    private LocalDateTime date;

    private int quantity;

    @ManyToOne
    private Order order;
}
