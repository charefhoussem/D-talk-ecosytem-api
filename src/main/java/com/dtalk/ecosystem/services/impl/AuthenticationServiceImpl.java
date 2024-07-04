package com.dtalk.ecosystem.services.impl;

import com.dtalk.ecosystem.DTOs.request.authentication.*;
import com.dtalk.ecosystem.DTOs.response.JwtAuthenticationResponse;
import com.dtalk.ecosystem.entities.*;
import com.dtalk.ecosystem.entities.users.*;
import com.dtalk.ecosystem.exceptions.ResourceInvalidException;
import com.dtalk.ecosystem.exceptions.ResourceNotFoundException;
import com.dtalk.ecosystem.exceptions.TokenExpiredException;
import com.dtalk.ecosystem.exceptions.UserAlreadyExistsException;
import com.dtalk.ecosystem.repositories.*;
import com.dtalk.ecosystem.services.AuthenticationService;
import com.dtalk.ecosystem.services.EmailService;
import com.dtalk.ecosystem.services.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@AllArgsConstructor

public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final AdminRepository adminRepository;
    private final FashionDesignerRepository fashionDesignerRepository;
    private final BrandRepository brandRepository;
    private final DesignerRepository designerRepository;
    private final ProductionTypeRepository productionTypeRepository;
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
    public Designer signupDesigner(SignUpDesignerAndFashionRequest request) throws IOException {
        // Vérifier si l'utilisateur existe déjà
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("User with email " + request.getEmail() + " already exists");
        }

        String codeVerification = VerificationCodeGenerateService.generateCode();

        Designer d = new Designer();
        d.setName(request.getName());
        d.setLastname(request.getLastname());
        d.setEmail(request.getEmail());
        d.setPassword(passwordEncoder.encode(request.getPassword()));
        d.setRole(Role.valueOf("DESIGNER"));
        d.setCodeVerification(codeVerification);
        d.setEnable(false);
        d.setLocked(false);
        d.setCountry(request.getCountry());
        d.setCountryCode(request.getCountryCode());
        d.setPhone(request.getPhone());
        d.setInstagramUrl(request.getInstagramUrl());
        d.setDescription(request.getDescription());

            String imageUrl = fileStorageService.saveFile(request.getImageFile());
            d.setImageUrl(imageUrl);


        designerRepository.save(d);
        emailService.confirmationSignup(d.getEmail(),"Code Verification",codeVerification);
        return d;

    }

    @Override
    public FashionDesigner signupFashionDesigner(SignUpDesignerAndFashionRequest request) throws IOException {
        // Vérifier si l'utilisateur existe déjà
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("User with email " + request.getEmail() + " already exists");
        }

        String codeVerification = VerificationCodeGenerateService.generateCode();

        FashionDesigner fd = new FashionDesigner();
        fd.setName(request.getName());
        fd.setLastname(request.getLastname());
        fd.setEmail(request.getEmail());
        fd.setPassword(passwordEncoder.encode(request.getPassword()));
        fd.setRole(Role.valueOf("FASHIONDESIGNER"));
        fd.setCodeVerification(codeVerification);
        fd.setEnable(false);
        fd.setLocked(false);
        fd.setCountry(request.getCountry());
        fd.setCountryCode(request.getCountryCode());
        fd.setPhone(request.getPhone());
        fd.setInstagramUrl(request.getInstagramUrl());
        fd.setDescription(request.getDescription());

        String imageUrl = fileStorageService.saveFile(request.getImageFile());
        fd.setImageUrl(imageUrl);


        fashionDesignerRepository.save(fd);
        emailService.confirmationSignup(fd.getEmail(),"Code Verification",codeVerification);
        return fd;
    }

    @Override
    public Brand signupBrand(SignUpBrandRequest request) throws IOException {
        // Vérifier si l'utilisateur existe déjà
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("User with email " + request.getEmail() + " already exists");
        }

        String codeVerification = VerificationCodeGenerateService.generateCode();

        Brand b = new Brand();
        b.setName(request.getName());
        b.setEmail(request.getEmail());
        b.setPassword(passwordEncoder.encode(request.getPassword()));
        b.setRole(Role.valueOf("BRAND"));
        b.setCodeVerification(codeVerification);
        b.setEnable(false);
        b.setLocked(false);
        b.setCountry(request.getCountry());
        b.setCountryCode(request.getCountryCode());
        b.setPhone(request.getPhone());

        String imageUrl = fileStorageService.saveFile(request.getImageFile());
        b.setImageUrl(imageUrl);
        b.setBrandAge(request.getBrandAge());
        Set<ProductionType> prodTypes = getOrCreateProductionType(request.getProductionTypes());
        b.setProductionTypes(prodTypes);

        brandRepository.save(b);
        emailService.confirmationSignup(b.getEmail(),"Code Verification",codeVerification);
        return b;
    }

    @Override
    public Admin signupAdmin(SignUpAdminRequest request) throws IOException {

        // Vérifier si l'utilisateur existe déjà
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("User with email " + request.getEmail() + " already exists");
        }

        String codeVerification = VerificationCodeGenerateService.generateCode();

        Admin ad = new Admin();
        ad.setName(request.getName());
        ad.setLastname(request.getLastname());
        ad.setEmail(request.getEmail());
        ad.setPassword(passwordEncoder.encode(request.getPassword()));
        ad.setRole(Role.valueOf("ADMIN"));
        ad.setCodeVerification(codeVerification);
        ad.setEnable(false);
        ad.setLocked(false);
        ad.setCountry(request.getCountry());
        ad.setCountryCode(request.getCountryCode());
        ad.setPhone(request.getPhone());


        String imageUrl = fileStorageService.saveFile(request.getImageFile());
        ad.setImageUrl(imageUrl);


        adminRepository.save(ad);
        emailService.confirmationSignup(ad.getEmail(),"Code Verification",codeVerification);
        return ad;    }

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

    private Set<ProductionType> getOrCreateProductionType(List<String> prTypes) {
        Set<ProductionType> productionTypes = new HashSet<>();
        for (String prodType : prTypes) {
            ProductionType prt = productionTypeRepository.findAllByType(prodType)
                    .orElseGet(() -> ProductionType.builder().type(prodType).build());
            productionTypes.add(prt);
        }
        return productionTypes;
    }


}
