package com.dtalk.ecosystem.services.impl;

import com.dtalk.ecosystem.DTOs.request.SignUpRequest;
import com.dtalk.ecosystem.DTOs.request.SigninRequest;
import com.dtalk.ecosystem.DTOs.response.JwtAuthenticationResponse;
import com.dtalk.ecosystem.entities.User;
import com.dtalk.ecosystem.repositories.UserRepository;
import com.dtalk.ecosystem.services.EmailService;
import com.dtalk.ecosystem.services.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
public class AuthenticationServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private EmailService emailService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthenticationServiceImpl authService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
/*
    @Test
    public void testSignup() {
        // Given
        SignUpRequest request = new SignUpRequest();
        request.setName("hass");
        request.setLastname("mrak");
        request.setEmail("hass.mrak@test.com");
        request.setPassword("password");
        request.setRole("ADMIN");

        // Mock UserRepository behavior
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User userToSave = invocation.getArgument(0);
            userToSave.setIdUser(1L); // Simulate saving user and generating ID
            return userToSave;
        });

        // Mock EmailService behavior
        doNothing().when(emailService).confirmationSignup(anyString(), anyString(), anyString());

        // When
        User savedUser = authService.signup(request);

        // Then
        assertNotNull(savedUser);
        assertEquals("hass", savedUser.getName());
        assertEquals("mrak", savedUser.getLastname());
        assertEquals("hass.mrak@test.com", savedUser.getEmail());
        assertEquals(Role.ADMIN, savedUser.getRole());
        assertNotNull(savedUser.getCodeVerification());
        assertFalse(savedUser.isEnabled());
        assertFalse(savedUser.isAccountNonLocked());

        verify(userRepository, times(1)).save(any(User.class));
        verify(emailService, times(1)).confirmationSignup(anyString(), anyString(), anyString());
    }
*/
    @Test
    public void testSignin_ValidCredentials() {
        // Given
        SigninRequest request = new SigninRequest();
        request.setEmail("john.doe@example.com");
        request.setPassword("password");

        User mockUser = new User();
        mockUser.setEmail("john.doe@example.com");
        mockUser.setPassword(passwordEncoder.encode("password"));

        // Mock UserRepository behavior
        when(userRepository.findByEmail("john.doe@example.com")).thenReturn(Optional.of(mockUser));

        // Mock JwtService behavior
        when(jwtService.generateToken(any(User.class))).thenReturn("mocked-jwt-token");

        // Mock AuthenticationManager behavior
        // Use when().thenReturn() to specify return value
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(null); // You can return whatever you need here for successful authentication

        // When
        JwtAuthenticationResponse response = authService.signin(request);

        // Then
        assertNotNull(response);
        assertEquals("mocked-jwt-token", response.getToken());

        verify(userRepository, times(1)).findByEmail("john.doe@example.com");
        verify(jwtService, times(1)).generateToken(any(User.class));
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }

    @Test
    public void testSignin_InvalidCredentials() {
        // Given
        SigninRequest request = new SigninRequest();
        request.setEmail("john.doe@example.com");
        request.setPassword("wrong-password");

        // Mock AuthenticationManager behavior to throw an exception
        doThrow(new IllegalArgumentException("Invalid email or password.")).when(authenticationManager)
                .authenticate(any());

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            authService.signin(request);
        });

        verify(authenticationManager, times(1)).authenticate(any());
    }

    @Test
    public void testVerifCode_ValidCode() {
        // Given
        String code = "valid-code";
        User mockUser = new User();
        mockUser.setLocked(false);
        when(userRepository.findByVerificationCode(code)).thenReturn(mockUser);

        // When
        boolean result = authService.verifCode(code);

        // Then
        assertTrue(result);
        assertTrue(mockUser.getLocked());

        verify(userRepository, times(1)).findByVerificationCode(code);
        verify(userRepository, times(1)).save(mockUser);
    }
    @Test
    public void testEnableUser() {
        // Given
        long userId = 1L;
        User mockUser = new User();
        mockUser.setIdUser(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));

        // When
        JwtAuthenticationResponse response = authService.enableUser(userId);

        // Then
        assertNotNull(response);
        assertTrue(mockUser.getEnable());

        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).save(mockUser);
        verify(jwtService, times(1)).generateToken(mockUser);
    }
    @Test
    public void testDisableUser() {
        // Given
        long userId = 1L;
        User mockUser = new User();
        mockUser.setIdUser(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));

        // When
        JwtAuthenticationResponse response = authService.disableUser(userId);

        // Then
        assertNotNull(response);
        assertFalse(mockUser.getEnable());

        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).save(mockUser);
        verify(jwtService, never()).generateToken(any(User.class)); // Vérifie qu'aucun token n'est généré lors de la désactivation
    }

}
