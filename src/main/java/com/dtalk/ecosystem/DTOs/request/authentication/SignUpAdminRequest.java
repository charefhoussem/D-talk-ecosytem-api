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
public class SignUpAdminRequest {

    private String name;
    private String lastname;
    private String email;
    private String password;
    private String country;
    private String countryCode;
    private MultipartFile imageFile;
    private String phone;
<<<<<<< HEAD:src/main/java/com/dtalk/ecosystem/DTOs/request/SignUpRequest.java
    private String description;
    private String instagramUrl;


=======
>>>>>>> FashionDesigner:src/main/java/com/dtalk/ecosystem/DTOs/request/authentication/SignUpAdminRequest.java
}
