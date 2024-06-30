package com.dtalk.ecosystem.DTOs.request.authentication;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {
    private String name;
    private String lastname;
    private String email;
    private String password;
    private String role;
    private String country;
    private String countryCode;
    private String phone;
    private String description;
    private String instagramUrl;

}
