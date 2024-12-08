package com.dtalk.ecosystem.controllers;

import com.dtalk.ecosystem.DTOs.response.dashboard_Designer.DesignerStatDTO;
import com.dtalk.ecosystem.services.DashboardDesignerService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dashboard")
@AllArgsConstructor
public class DashboradController {

    private final DashboardDesignerService dashboardDesignerService;

    @PreAuthorize("hasRole('DESIGNER')")
    @GetMapping("/{idDesigner}/stats")
    public ResponseEntity<DesignerStatDTO> getDesignerStats(@PathVariable Long idDesigner) {
        DesignerStatDTO stats = dashboardDesignerService.getDesignerStats(idDesigner);
        return ResponseEntity.ok(stats);
    }
}
