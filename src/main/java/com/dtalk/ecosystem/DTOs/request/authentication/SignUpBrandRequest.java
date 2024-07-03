package com.dtalk.ecosystem.DTOs.request.authentication;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpBrandRequest {
    private String name;
    private String email;
    private String password;
    private String role;
    private String country;
    private String countryCode;
    private String phone;
    private int BrandAge;
    private MultipartFile imageFile;

    private List<String> productionTypes;




}
