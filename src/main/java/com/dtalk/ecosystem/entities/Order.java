package com.dtalk.ecosystem.entities;

import com.dtalk.ecosystem.entities.enumiration.EtatOrder;
import com.dtalk.ecosystem.entities.users.Brand;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idOrder;

    private LocalDateTime date = LocalDateTime.now();

    private Boolean isValid = false;

    private Double amount = 0.0;

    @Enumerated(EnumType.STRING)
    private EtatOrder etat = EtatOrder.PROTOTYPING;

    private int quantity = 0;

    @ManyToMany(cascade = CascadeType.ALL)
    private Set<FolderStyle> folderStyles;

    @ManyToMany(cascade = CascadeType.ALL)
    private Set<Design> designs;


    @ManyToOne
    private Brand brand;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="order")
    private Set<Prototype> prototypes ;



}
