package com.dtalk.ecosystem.entities.users;

import com.dtalk.ecosystem.entities.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name ="users")
public class User implements  UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idUser;
    @NotBlank(message = "Name is mandatory")
    @Size(max = 50, message = "Name must be less than 50 characters")
    private String name;


    @Email(message = "Email must be valid")
    @NotBlank(message = "Email is mandatory")
    @Column(unique = true)
    private String email;

    @NotBlank(message = "Password is mandatory")
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;


    @NotNull(message = "Role is mandatory")
    @Enumerated(EnumType.STRING)
    private Role role;

    @JsonIgnore
    private String codeVerification;

    @NotNull(message = "Enable status is mandatory")
    @JsonIgnore
    private Boolean enable;

    @NotNull(message = "locked status is mandatory")
    @JsonIgnore
    private Boolean locked;

    @NotBlank(message = "country is mandatory")
    @Size(max = 10, message = "Country must be less than 100 characters")
    private String country;

    @Size(max = 4, message = "Country code must be less than 10 characters")
    private String countryCode;

    @Pattern(regexp = "^\\+?[0-9. ()-]{7,25}$", message = "Phone number must be valid")
    private String phone;

    @Size(max = 255, message = "Image URL must be less than 255 characters")
    private String imageUrl;





    // for reset_password
    private String resetPasswordToken;

    private LocalDateTime tokenExpirationTime;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    @JsonIgnore
    private List<Reclamation> reclamations;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enable;
    }
}
