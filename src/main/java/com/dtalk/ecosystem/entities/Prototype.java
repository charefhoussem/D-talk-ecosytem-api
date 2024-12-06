package com.dtalk.ecosystem.entities;

import com.dtalk.ecosystem.entities.enumiration.EtatPrototype;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name ="prototypes")
public class Prototype {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private Boolean isValid = false;

    @Enumerated(EnumType.STRING)
    private EtatPrototype etat = EtatPrototype.RECEIVED;

    private LocalDateTime date = LocalDateTime.now();

    @ManyToOne
    private Order order;
}
