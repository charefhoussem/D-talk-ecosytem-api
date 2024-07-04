package com.dtalk.ecosystem.services.impl;

import com.dtalk.ecosystem.entities.users.Admin;
import com.dtalk.ecosystem.repositories.AdminRepository;
import com.dtalk.ecosystem.services.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final AdminRepository adminRepository;
    @Override
    public List<Admin> retrieveAllAdmins() {
        return adminRepository.findAll();
    }
}
