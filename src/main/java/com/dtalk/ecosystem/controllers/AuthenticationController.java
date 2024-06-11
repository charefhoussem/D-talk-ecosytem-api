package com.dtalk.ecosystem.controllers;

import com.dtalk.ecosystem.DTOs.request.SignUpRequest;
import com.dtalk.ecosystem.DTOs.request.SigninRequest;
import com.dtalk.ecosystem.DTOs.response.JwtAuthenticationResponse;
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
    public ResponseEntity<JwtAuthenticationResponse> signup(@RequestBody SignUpRequest request) {
        return ResponseEntity.ok(authenticationService.signup(request));
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtAuthenticationResponse> signin(@RequestBody SigninRequest request) {
        return ResponseEntity.ok(authenticationService.signin(request));
    }

    @GetMapping("/verification-code")
    public ResponseEntity<Object> verifyUser(@RequestBody String code) {
        if (authenticationService.verifCode(code)) {
            return ResponseHandler.responseBuilder("verify_success", HttpStatus.OK, null);
        } else {
            return ResponseHandler.responseBuilder("unable to verify please try again", HttpStatus.INTERNAL_SERVER_ERROR, null);
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
