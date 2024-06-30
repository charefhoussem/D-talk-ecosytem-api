package com.dtalk.ecosystem.services.impl;

import com.dtalk.ecosystem.DTOs.request.ChangePasswordRequest;
import com.dtalk.ecosystem.DTOs.request.SignUpRequest;
import com.dtalk.ecosystem.DTOs.request.SigninRequest;
import com.dtalk.ecosystem.DTOs.response.JwtAuthenticationResponse;
import com.dtalk.ecosystem.entities.Role;
import com.dtalk.ecosystem.entities.User;
import com.dtalk.ecosystem.exceptions.ResourceInvalidException;
import com.dtalk.ecosystem.exceptions.ResourceNotFoundException;
import com.dtalk.ecosystem.exceptions.TokenExpiredException;
import com.dtalk.ecosystem.exceptions.UserAlreadyExistsException;
import com.dtalk.ecosystem.repositories.UserRepository;
import com.dtalk.ecosystem.services.AuthenticationService;
import com.dtalk.ecosystem.services.EmailService;
import com.dtalk.ecosystem.services.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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
    private FileStorageService fileStorageService;

    @Autowired
    public void setFileStorageService(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }
    @Override
    public User signup(SignUpRequest request, MultipartFile imageFile) throws IOException {
        // Vérifier si l'utilisateur existe déjà
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("User with email " + request.getEmail() + " already exists");
        }

        String codeVerification = VerificationCodeGenerateService.generateCode();

        User u = new User();
        u.setName(request.getName());
        u.setLastname(request.getLastname());
        u.setEmail(request.getEmail());
        u.setPassword(passwordEncoder.encode(request.getPassword()));
        u.setRole(Role.valueOf(request.getRole()));
        u.setCodeVerification(codeVerification);
        u.setEnable(false);
        u.setLocked(false);
        u.setCountry(request.getCountry());
        u.setCountryCode(request.getCountryCode());
        u.setPhone(request.getPhone());
        u.setInstagramUrl(request.getInstagramUrl());
        u.setDescription(request.getDescription());

            String imageUrl = fileStorageService.saveFile(imageFile);
            u.setImageUrl(imageUrl);


        userRepository.save(u);
        emailService.confirmationSignup(u.getEmail(),"Code Verification",codeVerification);
        return u;

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
        User user = userRepository.findByResetPasswordToken(token)
                .orElseThrow(() -> new ResourceInvalidException("Invalid token"));
        if (user.getTokenExpirationTime().isBefore(LocalDateTime.now())) {
            throw new TokenExpiredException("Token has expired");
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

    @Override
    public void changePassword(Long idUser, ChangePasswordRequest request) {

        User user = userRepository.findById(idUser).orElseThrow(()-> new ResourceNotFoundException("user not found" + idUser));
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new ResourceInvalidException("Current password does not match");
        }
        String encodedPassword = passwordEncoder.encode(request.getNewPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);
    }


}
