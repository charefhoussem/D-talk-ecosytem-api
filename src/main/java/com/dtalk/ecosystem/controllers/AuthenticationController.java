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

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<User> signup(@RequestBody SignUpRequest request) {
        return ResponseEntity.ok(authenticationService.signup(request));
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
    public ResponseEntity<String> forgotPassword(@RequestBody Map<String, String> payload) {
        String email = payload.get("email");
        authenticationService.initiatePasswordReset(email);
        return ResponseEntity.ok("Password reset link sent");
    }


    @GetMapping("/reset_password")
    public ResponseEntity<String> validateToken(@RequestParam("token") String token) {
        boolean isValid = authenticationService.validatePasswordResetToken(token);
        if (isValid) {
            return ResponseEntity.ok("Token is valid");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid or expired token");
        }
    }


    @PutMapping("/reset_password")
    public ResponseEntity<String> resetPassword(@RequestBody Map<String, String> payload) {
        String token = payload.get("token");
        String newPassword = payload.get("newPassword");
        try {
            authenticationService.resetPassword(token, newPassword);
            return ResponseEntity.ok("Password reset successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


}
