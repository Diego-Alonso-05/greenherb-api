package com.greenherb.greenherb_api.service;

import com.greenherb.greenherb_api.model.Alert;
import com.greenherb.greenherb_api.model.AlertStatus;
import com.greenherb.greenherb_api.repository.AlertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AlertService {

    private final AlertRepository alertRepository;

    // CREAR ALERTA AUTOMÁTICA
    public Alert createAlert(String message, String level) {

        Alert alert = new Alert();

        alert.setMessage(message);
        alert.setLevel(level);

        alert.setStatus(AlertStatus.ACTIVE);

        return alertRepository.save(alert);
    }

    // RESOLVER ALERTA
    public Alert resolveAlert(Long id) {

        Alert alert = alertRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Alert not found"));

        alert.setStatus(AlertStatus.RESOLVED);

        return alertRepository.save(alert);
    }

    // IGNORAR ALERTA
    public Alert ignoreAlert(Long id, String justification) {

        if (justification == null || justification.isBlank()) {
            throw new RuntimeException("Justification required");
        }

        Alert alert = alertRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Alert not found"));

        alert.setStatus(AlertStatus.IGNORED);

        alert.setJustification(justification);

        return alertRepository.save(alert);
    }
}