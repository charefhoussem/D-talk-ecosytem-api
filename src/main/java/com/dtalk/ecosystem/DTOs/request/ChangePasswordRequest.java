package com.dtalk.ecosystem.DTOs.request;


import lombok.Data;

@Data
public class ChangePasswordRequest {
    private String currentPassword;
    private String newPassword;

}
