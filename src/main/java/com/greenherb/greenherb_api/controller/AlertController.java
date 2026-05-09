package com.greenherb.greenherb_api.controller;

import com.greenherb.greenherb_api.model.Alert;
import com.greenherb.greenherb_api.repository.AlertRepository;
import com.greenherb.greenherb_api.service.AlertService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/alerts")
@RequiredArgsConstructor
public class AlertController {

    private final AlertRepository alertRepository;
    private final AlertService alertService;

    @GetMapping
    @PreAuthorize("hasAnyRole('RESPONSIBLE', 'ADMIN')")
    public List<Alert> getAlerts() {

        return alertRepository.findAll();
    }

    @PatchMapping("/{id}/resolve")
    @PreAuthorize("hasAnyRole('RESPONSIBLE', 'ADMIN')")
    public Alert resolveAlert(@PathVariable Long id) {

        return alertService.resolveAlert(id);
    }

    @PatchMapping("/{id}/ignore")
    @PreAuthorize("hasAnyRole('RESPONSIBLE', 'ADMIN')")
    public Alert ignoreAlert(
            @PathVariable Long id,
            @RequestBody Map<String, String> body
    ) {

        return alertService.ignoreAlert(
                id,
                body.get("justification")
        );
    }
}