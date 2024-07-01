package com.dtalk.ecosystem.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
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

    private Date date;

    private Double amount;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="order")
    private Set<FolderStyle> folderStyles ;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="order")
    private Set<Design> designs ;



}
