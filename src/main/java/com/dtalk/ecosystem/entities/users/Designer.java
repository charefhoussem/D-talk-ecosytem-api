package com.dtalk.ecosystem.entities.users;

import com.dtalk.ecosystem.entities.BankTransaction;
import com.dtalk.ecosystem.entities.Design;
import com.dtalk.ecosystem.entities.users.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
import jakarta.validation.constraints.*;
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name ="designers")

public class Designer extends User {

     @NotBlank(message = "Lastname is mandatory")
     @Size(max = 50, message = "Lastname must be less than 50 characters")
     private String lastname;

    private String description;
    private String instagramUrl;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="designer")
    @JsonIgnore
    private Set<Design> designs;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="designer")
    private Set<BankTransaction> bankTransactions = new HashSet<>() ;
}
