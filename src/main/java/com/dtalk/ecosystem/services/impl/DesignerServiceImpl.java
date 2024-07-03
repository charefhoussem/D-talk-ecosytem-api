package com.dtalk.ecosystem.services.impl;

import com.dtalk.ecosystem.entities.users.Designer;
import com.dtalk.ecosystem.repositories.DesignerRepository;
import com.dtalk.ecosystem.services.DesignerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class DesignerServiceImpl implements DesignerService {
    private final DesignerRepository designerRepository;
    @Override
    public List<Designer> retrieveAllDesginers() {
        return designerRepository.findAll();
    }
}
