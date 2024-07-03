package com.dtalk.ecosystem.entities;

import com.dtalk.ecosystem.entities.users.Brand;
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
    private long idProdType;

    @NotBlank(message = "type is mandatory")
    private String type;


    @ManyToMany(mappedBy="productionTypes", cascade = CascadeType.ALL)
    private Set<Brand> brands;
}
