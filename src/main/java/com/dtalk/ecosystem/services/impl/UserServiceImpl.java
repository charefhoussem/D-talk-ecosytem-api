package com.dtalk.ecosystem.services.impl;

import com.dtalk.ecosystem.DTOs.request.ChangePasswordRequest;
import com.dtalk.ecosystem.entities.Role;
import com.dtalk.ecosystem.entities.User;
import com.dtalk.ecosystem.exceptions.ResourceInvalidException;
import com.dtalk.ecosystem.exceptions.ResourceNotFoundException;
import com.dtalk.ecosystem.exceptions.UserAlreadyExistsException;
import com.dtalk.ecosystem.repositories.UserRepository;
import com.dtalk.ecosystem.services.UserService;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.utility.RandomString;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) {
                return userRepository.findByEmail(username)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            }
        };
    }

    @Override
    public User getUserById(Long idUser) {
        return userRepository.findById(idUser)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + idUser));
    }

    @Override
    public List<User> retrieveAllUserByRole(String role) {
        try {
            return userRepository.findByRole(Role.valueOf(role));
        } catch (IllegalArgumentException e) {
            throw new ResourceInvalidException("Invalid role: " + role);
        }
    }







}
