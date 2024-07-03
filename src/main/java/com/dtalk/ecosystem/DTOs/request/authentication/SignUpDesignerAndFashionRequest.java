package com.dtalk.ecosystem.DTOs.request.authentication;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpDesignerAndFashionRequest {
    private String name;
    private String lastname;
    private String email;
    private String password;
    private String country;
    private String countryCode;
    private String phone;
    private MultipartFile imageFile;
    private String description;
    private String instagramUrl;

}
