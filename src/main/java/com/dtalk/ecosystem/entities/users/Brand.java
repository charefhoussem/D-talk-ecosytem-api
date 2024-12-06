package com.dtalk.ecosystem.entities.users;

import com.dtalk.ecosystem.entities.Order;
import com.dtalk.ecosystem.entities.ProductionType;
import com.dtalk.ecosystem.entities.users.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name ="brands")

public class Brand extends User {

    private int brandAge;

    @ManyToMany(cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<ProductionType> productionTypes;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "brand")
    @JsonIgnore
    private List<Order> orders;
}
