package com.dtalk.ecosystem.controllers;

import com.dtalk.ecosystem.DTOs.request.authentication.*;
import com.dtalk.ecosystem.DTOs.response.JwtAuthenticationResponse;
import com.dtalk.ecosystem.entities.users.Admin;
import com.dtalk.ecosystem.entities.users.Brand;
import com.dtalk.ecosystem.entities.users.Designer;
import com.dtalk.ecosystem.entities.users.FashionDesigner;
import com.dtalk.ecosystem.response.ResponseHandler;
import com.dtalk.ecosystem.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/signup-designer")
    public ResponseEntity<Designer> signupDesigner(
            @RequestParam("name") String name,
            @RequestParam("lastname") String lastname,
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("country") String country,
            @RequestParam("countryCode") String countryCode,
            @RequestParam("phone") String phone,
            @RequestParam("imageFile") MultipartFile imageFile,
            @RequestParam("description") String description,
            @RequestParam("instagram") String instagram

            ) throws IOException {
        SignUpDesignerAndFashionRequest signUpDesignerRequest = new SignUpDesignerAndFashionRequest();
        signUpDesignerRequest.setName(name);
        signUpDesignerRequest.setLastname(lastname);
        signUpDesignerRequest.setEmail(email);
        signUpDesignerRequest.setPassword(password);
        signUpDesignerRequest.setCountry(country);
        signUpDesignerRequest.setCountryCode(countryCode);
        signUpDesignerRequest.setPhone(phone);
        signUpDesignerRequest.setImageFile(imageFile);
        signUpDesignerRequest.setInstagramUrl(instagram);
        signUpDesignerRequest.setDescription(description);

        return ResponseEntity.ok(authenticationService.signupDesigner(signUpDesignerRequest));
    }


    @PostMapping("/signup-fashion-designer")
    public ResponseEntity<FashionDesigner> signupFashionDesigner(
            @RequestParam("name") String name,
            @RequestParam("lastname") String lastname,
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("country") String country,
            @RequestParam("countryCode") String countryCode,
            @RequestParam("phone") String phone,
            @RequestParam("imageFile") MultipartFile imageFile,
            @RequestParam("description") String description,
            @RequestParam("instagram") String instagram

    ) throws IOException {
        SignUpDesignerAndFashionRequest signUpFDesignerRequest = new SignUpDesignerAndFashionRequest();
        signUpFDesignerRequest.setName(name);
        signUpFDesignerRequest.setLastname(lastname);
        signUpFDesignerRequest.setEmail(email);
        signUpFDesignerRequest.setPassword(password);
        signUpFDesignerRequest.setCountry(country);
        signUpFDesignerRequest.setCountryCode(countryCode);
        signUpFDesignerRequest.setPhone(phone);
        signUpFDesignerRequest.setImageFile(imageFile);
        signUpFDesignerRequest.setInstagramUrl(instagram);
        signUpFDesignerRequest.setDescription(description);

        return ResponseEntity.ok(authenticationService.signupFashionDesigner(signUpFDesignerRequest));
    }


    @PostMapping("/signup-admin")
    public ResponseEntity<Admin> signupAdmin(
            @RequestParam("name") String name,
            @RequestParam("lastname") String lastname,
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("country") String country,
            @RequestParam("countryCode") String countryCode,
            @RequestParam("phone") String phone,

            @RequestParam("description") String description,
            @RequestParam("instagram") String instagramUrl,
            @RequestParam("imageFile") MultipartFile imageFile



    ) throws IOException {
        SignUpAdminRequest signUpAdminRequest = new SignUpAdminRequest();
        signUpAdminRequest.setName(name);
        signUpAdminRequest.setLastname(lastname);
        signUpAdminRequest.setEmail(email);
        signUpAdminRequest.setPassword(password);
        signUpAdminRequest.setCountry(country);
        signUpAdminRequest.setCountryCode(countryCode);
        signUpAdminRequest.setPhone(phone);
        signUpAdminRequest.setImageFile(imageFile);


        return ResponseEntity.ok(authenticationService.signupAdmin(signUpAdminRequest));
    }


    @PostMapping("/signup-brand")
    public ResponseEntity<Brand> signupBrand(
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("country") String country,
            @RequestParam("countryCode") String countryCode,
            @RequestParam("phone") String phone,
            @RequestParam("imageFile") MultipartFile imageFile,
            @RequestParam("brandAge") int brandAge,
            @RequestParam("productionTypes") List<String> productionTypes

    ) throws IOException {
        SignUpBrandRequest signBrandRequest = new SignUpBrandRequest();
        signBrandRequest.setName(name);
        signBrandRequest.setEmail(email);
        signBrandRequest.setPassword(password);
        signBrandRequest.setCountry(country);
        signBrandRequest.setCountryCode(countryCode);
        signBrandRequest.setPhone(phone);
        signBrandRequest.setImageFile(imageFile);
        signBrandRequest.setBrandAge(brandAge);
        signBrandRequest.setProductionTypes(productionTypes);

        return ResponseEntity.ok(authenticationService.signupBrand(signBrandRequest));
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtAuthenticationResponse> signin(@RequestBody SigninRequest request) {
        return ResponseEntity.ok(authenticationService.signin(request));
    }

    @PutMapping("/verification-code")
    public ResponseEntity<Object> verifyUser(@RequestBody VerifCodeRequest verif_c) {
        String code = verif_c.getCode();
        if (code == null || code.isEmpty()) {
            return ResponseHandler.responseBuilder("Verification code is missing", HttpStatus.BAD_REQUEST, null);

        }
        if (authenticationService.verifCode(code)) {
            return ResponseHandler.responseBuilder("User verified successfully", HttpStatus.OK, null);
        } else {
            return ResponseHandler.responseBuilder("Invalid verification code", HttpStatus.BAD_REQUEST, null);
        }
    }


    @PutMapping("/enable/{id}")
    public ResponseEntity<JwtAuthenticationResponse> enableUser(@PathVariable("id") Long id) {
        return ResponseEntity.ok(authenticationService.enableUser(id));
    }




    @PutMapping ("/disable/{id}")
    public ResponseEntity<JwtAuthenticationResponse> disableUser(@PathVariable("id") Long id) {
        return ResponseEntity.ok(authenticationService.disableUser(id));

    }


    @PostMapping("/forgot_password")
    public ResponseEntity<Object> forgotPassword(@RequestBody Map<String, String> payload) {
        String email = payload.get("email");
        authenticationService.initiatePasswordReset(email);
        return ResponseHandler.responseBuilder("Password reset link sent", HttpStatus.OK, null);

    }


    @GetMapping("/reset_password")
    public ResponseEntity<Object> validateToken(@RequestParam("token") String token) {
        boolean isValid = authenticationService.validatePasswordResetToken(token);
        return ResponseHandler.responseBuilder("Token is valid", HttpStatus.OK, null);
    }


    @PutMapping("/reset_password")
    public ResponseEntity<Object> resetPassword(@RequestBody Map<String, String> payload) {
        String token = payload.get("token");
        String newPassword = payload.get("newPassword");
        try {
            authenticationService.resetPassword(token, newPassword);
            return ResponseHandler.responseBuilder("Password reset successfully", HttpStatus.OK, null);


        } catch (IllegalArgumentException e) {
            return ResponseHandler.responseBuilder(e.getMessage(), HttpStatus.BAD_REQUEST, null);

        }
    }

    @PutMapping("/change-password/{idUser}")
    public ResponseEntity<Object> changePassword(@PathVariable("idUser") Long idUser, @RequestBody ChangePasswordRequest request){
        authenticationService.changePassword(idUser,request);
        return ResponseEntity.ok().build();

    }


}
