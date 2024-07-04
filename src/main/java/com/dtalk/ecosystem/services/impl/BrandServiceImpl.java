package com.dtalk.ecosystem.services.impl;

import com.dtalk.ecosystem.entities.users.Brand;
import com.dtalk.ecosystem.repositories.BrandRepository;
import com.dtalk.ecosystem.services.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {
    private final BrandRepository brandRepository;
    @Override
    public List<Brand> retrieveAllBrands() {
        return brandRepository.findAll() ;
    }
}
