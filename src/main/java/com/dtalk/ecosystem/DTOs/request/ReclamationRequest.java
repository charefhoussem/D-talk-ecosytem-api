package com.dtalk.ecosystem.DTOs.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReclamationRequest {

    @NotBlank(message = "Description is mandatory")
    private String description;


}
