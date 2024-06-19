package com.dtalk.ecosystem.services.impl;

import com.dtalk.ecosystem.DTOs.request.SignUpRequest;
import com.dtalk.ecosystem.DTOs.request.SigninRequest;
import com.dtalk.ecosystem.DTOs.response.JwtAuthenticationResponse;
import com.dtalk.ecosystem.entities.Role;
import com.dtalk.ecosystem.entities.User;
import com.dtalk.ecosystem.exceptions.ResourceInvalidException;
import com.dtalk.ecosystem.exceptions.ResourceNotFoundException;
import com.dtalk.ecosystem.exceptions.UserAlreadyExistsException;
import com.dtalk.ecosystem.repositories.UserRepository;
import com.dtalk.ecosystem.services.AuthenticationService;
import com.dtalk.ecosystem.services.EmailService;
import com.dtalk.ecosystem.services.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor

public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final EmailService emailService;
    private final AuthenticationManager authenticationManager;
    @Override
    public User signup(SignUpRequest request) {
        // Vérifier si l'utilisateur existe déjà
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("User with email " + request.getEmail() + " already exists");
        }

        String codeVerification = VerificationCodeGenerateService.generateCode();

        var user = User.builder().name(request.getName()).lastname(request.getLastname())
                .email(request.getEmail()).password(passwordEncoder.encode(request.getPassword()))
                .role(Role.valueOf(request.getRole())).codeVerification(codeVerification).enable(false).locked(false).build();

        userRepository.save(user);
        emailService.confirmationSignup(user.getEmail(),"Code Verification",codeVerification);
        return user;

    }

    @Override
    public JwtAuthenticationResponse signin(SigninRequest request) throws BadCredentialsException {

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));



        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + request.getEmail()));

        var jwt = jwtService.generateToken(user);

        return JwtAuthenticationResponse.builder().token(jwt).build();
    }

    @Override
    public Boolean verifCode(String code) {
        User user = userRepository.findByVerificationCode(code);
        if (user == null || user.getLocked()) {
            throw new ResourceInvalidException("Invalid or already used verification code");
        }
        user.setLocked(true);
        user.setCodeVerification(null); // Réinitialise le code de vérification après approbation
        userRepository.save(user);
        return true;

    }

    @Override
    public JwtAuthenticationResponse enableUser(Long idUser) {
        User user = userRepository.findById(idUser)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + idUser));
        user.setEnable(true);
        userRepository.save(user);

        var jwt = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder().token(jwt).build();


    }

    @Override
    public JwtAuthenticationResponse disableUser(Long idUser) {

        User user = userRepository.findById(idUser)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + idUser));
        user.setEnable(false);
        userRepository.save(user);
        return JwtAuthenticationResponse.builder().token(null).build();

    }

    @Override
    public void initiatePasswordReset(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        String token = UUID.randomUUID().toString();
        user.setResetPasswordToken(token);
        user.setTokenExpirationTime(LocalDateTime.now().plusHours(1));
        userRepository.save(user);

        String resetLink = "http://localhost:8089/v1/api/auth/reset_password?token=" + token;
        emailService.resetPassword(user.getEmail(), resetLink);
    }

    @Override
    public boolean validatePasswordResetToken(String token) {
        User user = userRepository.findByResetPasswordToken(token).orElseThrow(() -> new ResourceInvalidException("Invalid token"));
        if (user.getTokenExpirationTime().isBefore(LocalDateTime.now())) {
            throw new ResourceInvalidException("Token has expired");
        }
        return true;
    }

    @Override
    public void resetPassword(String token, String newPassword) {
        User user = userRepository.findByResetPasswordToken(token)
                .orElseThrow(() -> new ResourceInvalidException("Invalid token"));

        if (user.getTokenExpirationTime().isBefore(LocalDateTime.now())) {
            throw new ResourceInvalidException("Token has expired");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        user.setResetPasswordToken(null);
        user.setTokenExpirationTime(null);
        userRepository.save(user);
    }


}
