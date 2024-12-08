package com.dtalk.ecosystem.services;

import com.dtalk.ecosystem.DTOs.response.dashboard_Designer.DesignerStatDTO;

public interface DashboardDesignerService {
    DesignerStatDTO getDesignerStats(Long idDesigner);

}
