package com.dtalk.ecosystem.entities;

import com.dtalk.ecosystem.entities.users.Designer;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name ="designs")

public class Design {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idDesign;

    @NotBlank(message = "Name is mandatory")
    @Size(max = 100, message = "Name must be less than 100 characters")
    private String name;

    @Size(max = 255, message = "Description must be less than 255 characters")
    private String description;

    @NotBlank(message = "Image path is mandatory")
    private String imagePath;

    @NotBlank(message = "Origin file path is mandatory")
    private String originFilePath;

    @NotNull(message = "Publish status is mandatory")
    private Boolean isPublished;

    @NotNull(message = "Acceptance status is mandatory")
    private Boolean isAccepted;

    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    @Digits(integer = 10, fraction = 2, message = "Price must be a valid amount")
    private double price;



    @Transient
    private MultipartFile imageFile;
    @Transient
    private MultipartFile originFile;

    @ManyToOne
    private Designer designer;

    @ManyToMany(cascade = CascadeType.ALL)
    private Set<Tag> tags;

    @ManyToMany(cascade = CascadeType.ALL)
    private Set<FieldDesigner> fields;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "design")
    private List<Reclamation> reclamations;

    @ManyToMany(mappedBy = "designs", cascade = CascadeType.ALL)
    private Set<Order> orders;

}
