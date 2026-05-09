package com.greenherb.greenherb_api.service;

import com.greenherb.greenherb_api.model.Alert;
import com.greenherb.greenherb_api.model.AlertStatus;
import com.greenherb.greenherb_api.model.Measurement;
import com.greenherb.greenherb_api.model.Plan;
import com.greenherb.greenherb_api.repository.AlertRepository;
import com.greenherb.greenherb_api.repository.MeasurementRepository;
import com.greenherb.greenherb_api.repository.PlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MeasurementService {

    private final MeasurementRepository measurementRepository;
    private final AlertRepository alertRepository;
    private final PlanRepository planRepository;

    public Measurement saveMeasurement(Measurement measurement) {

        measurement.setCreatedAt(LocalDateTime.now());

        Measurement savedMeasurement =
                measurementRepository.save(measurement);

        Plan activePlan = planRepository.findByActiveTrue()
                .orElseThrow(() ->
                        new RuntimeException("No active plan found")
                );

        boolean highTemperature =
                measurement.getTemperature() > activePlan.getMaxTemp();

        boolean lowTemperature =
                measurement.getTemperature() < activePlan.getMinTemp();

        boolean highHumidity =
                measurement.getHumidity() > activePlan.getMaxHumidity();

        boolean lowHumidity =
                measurement.getHumidity() < activePlan.getMinHumidity();

        if (highTemperature && lowHumidity) {

            boolean alertExists = alertRepository
                    .findByMessageAndLevelAndStatus(
                            "Critical environment conditions detected",
                            "CRITICAL",
                            AlertStatus.ACTIVE
                    )
                    .isPresent();

            if (!alertExists) {

                Alert alert = Alert.builder()
                        .message("Critical environment conditions detected")
                        .level("CRITICAL")
                        .status(AlertStatus.ACTIVE)
                        .createdAt(LocalDateTime.now())
                        .build();

                alertRepository.save(alert);
            }
        }

        if (highHumidity || lowTemperature) {

            boolean alertExists = alertRepository
                    .findByMessageAndLevelAndStatus(
                            "Warning conditions detected",
                            "WARNING",
                            AlertStatus.ACTIVE
                    )
                    .isPresent();

            if (!alertExists) {

                Alert alert = Alert.builder()
                        .message("Warning conditions detected")
                        .level("WARNING")
                        .status(AlertStatus.ACTIVE)
                        .createdAt(LocalDateTime.now())
                        .build();

                alertRepository.save(alert);
            }
        }

        return savedMeasurement;
    }
}