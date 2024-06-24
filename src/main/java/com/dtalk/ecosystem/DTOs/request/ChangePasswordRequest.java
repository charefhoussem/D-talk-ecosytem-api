package com.dtalk.ecosystem.DTOs.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class ChangePasswordRequest {
    private String currentPassword;
    private String newPassword;

}
