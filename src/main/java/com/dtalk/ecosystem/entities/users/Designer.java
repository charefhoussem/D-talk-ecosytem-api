package com.dtalk.ecosystem.entities.users;

import com.dtalk.ecosystem.entities.Design;
import com.dtalk.ecosystem.entities.users.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

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
}
