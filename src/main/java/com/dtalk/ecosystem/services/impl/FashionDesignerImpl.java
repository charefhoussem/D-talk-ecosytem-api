package com.dtalk.ecosystem.services.impl;

import com.dtalk.ecosystem.entities.users.FashionDesigner;
import com.dtalk.ecosystem.repositories.FashionDesignerRepository;
import com.dtalk.ecosystem.services.FashionDesignerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class FashionDesignerImpl implements FashionDesignerService {
    private final FashionDesignerRepository fashionDesignerRepository;
    @Override
    public List<FashionDesigner> retrieveAllFashionDesigners() {
        return fashionDesignerRepository.findAll() ;
    }
}
