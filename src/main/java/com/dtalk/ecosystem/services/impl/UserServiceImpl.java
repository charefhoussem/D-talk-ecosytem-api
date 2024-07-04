package com.dtalk.ecosystem.services.impl;

import com.dtalk.ecosystem.entities.Role;
import com.dtalk.ecosystem.entities.users.*;
import com.dtalk.ecosystem.exceptions.ResourceInvalidException;
import com.dtalk.ecosystem.exceptions.ResourceNotFoundException;
import com.dtalk.ecosystem.repositories.*;
import com.dtalk.ecosystem.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final BrandRepository brandRepository;
    private final FashionDesignerRepository fashionDesignerRepository;
    private final AdminRepository adminRepository;
    private final DesignerRepository designerRepository;

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

    @Override
    public List<Designer> retrieveAllDesigners() {
        return designerRepository.findAll();
    }

    @Override
    public List<Brand> retrieveAllBrands() {
        return brandRepository.findAll();
    }

    @Override
    public List<FashionDesigner> retrieveAllFashionDesigners() {
        return fashionDesignerRepository.findAll();
    }

    @Override
    public List<Admin> retrieveAllAdmins() {
        return adminRepository.findAll();
    }


}
