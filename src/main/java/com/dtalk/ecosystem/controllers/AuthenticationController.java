package com.dtalk.ecosystem.controllers;

import com.dtalk.ecosystem.DTOs.request.SignUpRequest;
import com.dtalk.ecosystem.DTOs.request.SigninRequest;
import com.dtalk.ecosystem.DTOs.request.VerifCodeRequest;
import com.dtalk.ecosystem.DTOs.response.JwtAuthenticationResponse;
import com.dtalk.ecosystem.entities.User;
import com.dtalk.ecosystem.response.ResponseHandler;
import com.dtalk.ecosystem.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
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

    @PostMapping("/signup")
    public ResponseEntity<User> signup(
            @RequestParam("name") String name,
            @RequestParam("lastname") String lastname,
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("role") String role,
            @RequestParam("country") String country,
            @RequestParam("countryCode") String countryCode,
            @RequestParam("phone") String phone,
            @RequestParam("imageFile") MultipartFile imageFile

            ) throws IOException {
        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setName(name);
        signUpRequest.setLastname(lastname);
        signUpRequest.setEmail(email);
        signUpRequest.setPassword(password);
        signUpRequest.setRole(role);
        signUpRequest.setCountry(country);
        signUpRequest.setCountryCode(countryCode);
        signUpRequest.setPhone(phone);
        return ResponseEntity.ok(authenticationService.signup(signUpRequest,imageFile));
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


}
