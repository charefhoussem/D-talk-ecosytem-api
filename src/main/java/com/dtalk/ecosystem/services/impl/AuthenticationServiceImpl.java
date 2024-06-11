package com.dtalk.ecosystem.services.impl;

import com.dtalk.ecosystem.DTOs.request.SignUpRequest;
import com.dtalk.ecosystem.DTOs.request.SigninRequest;
import com.dtalk.ecosystem.DTOs.response.JwtAuthenticationResponse;
import com.dtalk.ecosystem.entities.Role;
import com.dtalk.ecosystem.entities.User;
import com.dtalk.ecosystem.repositories.UserRepository;
import com.dtalk.ecosystem.services.AuthenticationService;
import com.dtalk.ecosystem.services.EmailService;
import com.dtalk.ecosystem.services.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final EmailService emailService;
    private final AuthenticationManager authenticationManager;
    @Override
    public JwtAuthenticationResponse signup(SignUpRequest request) {
        String codeVerification = VerificationCodeGenerateService.generateCode();

        var user = User.builder().name(request.getName()).lastname(request.getLastname())
                .email(request.getEmail()).password(passwordEncoder.encode(request.getPassword()))
                .role(Role.valueOf(request.getRole())).codeVerification(codeVerification).build();
        userRepository.save(user);
        emailService.confirmationSignup(user.getEmail(),"Verification ",codeVerification);
        var jwt = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder().token(jwt).build();

    }

    @Override
    public JwtAuthenticationResponse signin(SigninRequest request) {
        try {
            System.out.println("Attempting to authenticate user with email: " + request.getEmail());
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
            System.out.println("Authentication successful.");
        } catch (Exception e) {
            System.err.println("Authentication failed for email: " + request.getEmail());
            throw new IllegalArgumentException("Invalid email or password.");
        }

        System.out.println("Searching for user with email: " + request.getEmail());
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));
        System.out.println("User found: " + user.getEmail());

        System.out.println("Generating JWT token for user: " + user.getEmail());
        var jwt = jwtService.generateToken(user);
        System.out.println("JWT token generated: " + jwt);

        return JwtAuthenticationResponse.builder().token(jwt).build();
    }

    @Override
    public Boolean verifCode(String code) {
        User user = userRepository.findByVerificationCode(code);
        if (user == null) {
            return false;
        } else {
            user.setCodeVerification(null);
            userRepository.save(user);
            return true;
        }
    }
}
