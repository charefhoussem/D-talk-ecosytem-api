package com.dtalk.ecosystem.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Table(name = "folder_style")

public class FolderStyle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idFolder;

    @NotBlank(message = "Name is mandatory")
    @Size(max = 100, message = "Name must be less than 100 characters")
    private String name;

    @Size(max = 255, message = "Description must be less than 255 characters")
    private String description;

    @NotBlank(message = "type path is mandatory")
    private String type;

    @NotBlank(message = "file 3D file path is mandatory")
    private String  file_3d_Path;

    @NotNull(message = "Publish status is mandatory")
    private Boolean isPublished;

    @NotNull(message = "Acceptance status is mandatory")
    private Boolean isAccepted;

    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    @Digits(integer = 10, fraction = 2, message = "Price must be a valid amount")
    private double price;

}