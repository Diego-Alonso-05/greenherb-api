package com.greenherb.greenherb_api.service;

import com.greenherb.greenherb_api.model.Measurement;
import com.greenherb.greenherb_api.model.Plan;
import com.greenherb.greenherb_api.repository.AlertRepository;
import com.greenherb.greenherb_api.repository.MeasurementRepository;
import com.greenherb.greenherb_api.repository.PlanRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MeasurementServiceTest {

    @Mock
    private MeasurementRepository measurementRepository;

    @Mock
    private AlertRepository alertRepository;

    @Mock
    private PlanRepository planRepository;

    @InjectMocks
    private MeasurementService measurementService;

    private Plan activePlan() {
        return Plan.builder()
                .type("regular")
                .minTemp(18)
                .maxTemp(28)
                .minHumidity(40)
                .maxHumidity(80)
                .active(true)
                .automaticIrrigation(true)
                .build();
    }

    @Test
    void measurementInsideLimitsShouldNotCreateAlert() {
        Measurement measurement = Measurement.builder()
                .temperature(23)
                .humidity(60)
                .build();

        when(measurementRepository.save(any(Measurement.class))).thenReturn(measurement);
        when(planRepository.findByActiveTrue()).thenReturn(Optional.of(activePlan()));

        Measurement result = measurementService.saveMeasurement(measurement);

        assertEquals(23, result.getTemperature());
        assertEquals(60, result.getHumidity());
        verify(alertRepository, never()).save(any());
    }

    @Test
    void highTemperatureAndLowHumidityShouldCreateCriticalAlert() {
        Measurement measurement = Measurement.builder()
                .temperature(30)
                .humidity(30)
                .build();

        when(measurementRepository.save(any(Measurement.class))).thenReturn(measurement);
        when(planRepository.findByActiveTrue()).thenReturn(Optional.of(activePlan()));

        measurementService.saveMeasurement(measurement);

        verify(alertRepository, times(1)).save(argThat(alert ->
                alert.getLevel().equals("CRITICAL")
        ));
    }

    @Test
    void lowTemperatureShouldCreateWarningAlert() {
        Measurement measurement = Measurement.builder()
                .temperature(15)
                .humidity(60)
                .build();

        when(measurementRepository.save(any(Measurement.class))).thenReturn(measurement);
        when(planRepository.findByActiveTrue()).thenReturn(Optional.of(activePlan()));

        measurementService.saveMeasurement(measurement);

        verify(alertRepository, times(1)).save(argThat(alert ->
                alert.getLevel().equals("WARNING")
        ));
    }

    @Test
    void noActivePlanShouldThrowException() {
        Measurement measurement = Measurement.builder()
                .temperature(23)
                .humidity(60)
                .build();

        when(measurementRepository.save(any(Measurement.class))).thenReturn(measurement);
        when(planRepository.findByActiveTrue()).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> measurementService.saveMeasurement(measurement));
    }
}