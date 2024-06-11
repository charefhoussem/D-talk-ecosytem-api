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


}
