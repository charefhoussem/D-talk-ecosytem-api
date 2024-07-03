package com.dtalk.ecosystem.entities.users;


import com.dtalk.ecosystem.entities.users.User;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name ="admins")

public class Admin extends User {

    @NotBlank(message = "Lastname is mandatory")
    @Size(max = 50, message = "Lastname must be less than 50 characters")
    private String lastname;

}
