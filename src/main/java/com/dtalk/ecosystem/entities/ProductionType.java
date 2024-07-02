package com.dtalk.ecosystem.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductionType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idProd;

    @NotBlank(message = "type is mandatory")
    private String type;


    @ManyToMany(mappedBy="productionTypes", cascade = CascadeType.ALL)
    private Set<User> users;
}
