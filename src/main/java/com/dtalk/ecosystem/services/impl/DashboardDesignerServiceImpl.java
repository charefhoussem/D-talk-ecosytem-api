package com.dtalk.ecosystem.services.impl;

import com.dtalk.ecosystem.DTOs.response.dashboard_Designer.DesignerStatDTO;
import com.dtalk.ecosystem.repositories.DesignRepository;
import com.dtalk.ecosystem.repositories.OrderRepository;
import com.dtalk.ecosystem.services.DashboardDesignerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class DashboardDesignerServiceImpl implements DashboardDesignerService {
    @Autowired
    private final DesignRepository designRepository;

    @Autowired
    private final OrderRepository orderRepository;

    @Override
    public DesignerStatDTO getDesignerStats(Long idDesigner) {
        return null;
    }
}
