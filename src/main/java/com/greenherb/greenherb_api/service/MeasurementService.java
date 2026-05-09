package com.greenherb.greenherb_api.service;

import com.greenherb.greenherb_api.model.Alert;
import com.greenherb.greenherb_api.model.AlertStatus;
import com.greenherb.greenherb_api.model.Measurement;
import com.greenherb.greenherb_api.repository.AlertRepository;
import com.greenherb.greenherb_api.repository.MeasurementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MeasurementService {

    private final MeasurementRepository measurementRepository;
    private final AlertRepository alertRepository;

    public Measurement saveMeasurement(Measurement measurement) {

        measurement.setCreatedAt(LocalDateTime.now());

        Measurement savedMeasurement =
                measurementRepository.save(measurement);

        if (measurement.getTemperature() > 30) {

            Alert alert = Alert.builder()
                    .message("Temperature too high")
                    .level("CRITICAL")
                    .status(AlertStatus.ACTIVE)
                    .createdAt(LocalDateTime.now())
                    .build();

            alertRepository.save(alert);
        }

        if (measurement.getHumidity() > 70) {

            Alert alert = Alert.builder()
                    .message("Humidity too high")
                    .level("WARNING")
                    .status(AlertStatus.ACTIVE)
                    .createdAt(LocalDateTime.now())
                    .build();

            alertRepository.save(alert);
        }

        return savedMeasurement;
    }
}