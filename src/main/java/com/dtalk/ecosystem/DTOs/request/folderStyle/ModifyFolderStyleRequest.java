package com.dtalk.ecosystem.DTOs.request.folderStyle;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;
@Data
public class ModifyFolderStyleRequest {

    private Long id;

    @NotBlank(message = "Name is mandatory")
    @Size(max = 100, message = "Name must be less than 100 characters")
    private String name;

    @Size(max = 255, message = "Description must be less than 255 characters")
    private String description;

    @NotBlank(message = "type  is mandatory")
    private String type;

    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    @Digits(integer = 10, fraction = 2, message = "Price must be a valid amount")
    private double price;

    List<String > fields;
}
