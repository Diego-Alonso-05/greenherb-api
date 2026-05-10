package com.greenherb.greenherb_api.service;

import com.greenherb.greenherb_api.model.Alert;
import com.greenherb.greenherb_api.model.AlertStatus;
import com.greenherb.greenherb_api.repository.AlertRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AlertServiceTest {

    @Mock
    private AlertRepository alertRepository;

    @InjectMocks
    private AlertService alertService;

    @Test
    void createAlertShouldSaveActiveAlert() {
        when(alertRepository.save(any(Alert.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Alert result = alertService.createAlert("Test message", "WARNING");

        assertEquals("Test message", result.getMessage());
        assertEquals("WARNING", result.getLevel());
        assertEquals(AlertStatus.ACTIVE, result.getStatus());
        verify(alertRepository, times(1)).save(any(Alert.class));
    }

    @Test
    void resolveExistingAlertShouldSetStatusResolved() {
        Alert alert = Alert.builder()
                .id(1L)
                .message("Test")
                .level("WARNING")
                .status(AlertStatus.ACTIVE)
                .build();

        when(alertRepository.findById(1L)).thenReturn(Optional.of(alert));
        when(alertRepository.save(any(Alert.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Alert result = alertService.resolveAlert(1L);

        assertEquals(AlertStatus.RESOLVED, result.getStatus());
        verify(alertRepository).save(alert);
    }

    @Test
    void resolveNonExistingAlertShouldThrowException() {
        when(alertRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> alertService.resolveAlert(99L));
    }

    @Test
    void ignoreAlertWithJustificationShouldSetStatusIgnored() {
        Alert alert = Alert.builder()
                .id(1L)
                .message("Test")
                .level("WARNING")
                .status(AlertStatus.ACTIVE)
                .build();

        when(alertRepository.findById(1L)).thenReturn(Optional.of(alert));
        when(alertRepository.save(any(Alert.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Alert result = alertService.ignoreAlert(1L, "Sensor error");

        assertEquals(AlertStatus.IGNORED, result.getStatus());
        assertEquals("Sensor error", result.getJustification());
    }

    @Test
    void ignoreAlertWithoutJustificationShouldThrowException() {
        assertThrows(RuntimeException.class, () -> alertService.ignoreAlert(1L, ""));
        verify(alertRepository, never()).save(any());
    }
}