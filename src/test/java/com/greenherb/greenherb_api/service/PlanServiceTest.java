package com.greenherb.greenherb_api.service;

import com.greenherb.greenherb_api.model.Plan;
import com.greenherb.greenherb_api.repository.PlanRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlanServiceTest {

    @Mock
    private PlanRepository planRepository;

    @InjectMocks
    private PlanService planService;

    @Test
    void createValidPlanShouldSavePlan() {
        Plan plan = Plan.builder()
                .type("regular")
                .minTemp(18)
                .maxTemp(28)
                .minHumidity(40)
                .maxHumidity(80)
                .active(true)
                .automaticIrrigation(true)
                .build();

        when(planRepository.save(any(Plan.class))).thenReturn(plan);

        Plan result = planService.createPlan(plan);

        assertEquals("regular", result.getType());
        verify(planRepository, times(1)).save(plan);
    }

    @Test
    void createPlanWithInvalidTemperatureRangeShouldThrowException() {
        Plan plan = Plan.builder()
                .type("regular")
                .minTemp(30)
                .maxTemp(20)
                .minHumidity(40)
                .maxHumidity(80)
                .build();

        assertThrows(RuntimeException.class, () -> planService.createPlan(plan));
        verify(planRepository, never()).save(any());
    }

    @Test
    void createPlanWithInvalidHumidityRangeShouldThrowException() {
        Plan plan = Plan.builder()
                .type("regular")
                .minTemp(18)
                .maxTemp(28)
                .minHumidity(90)
                .maxHumidity(80)
                .build();

        assertThrows(RuntimeException.class, () -> planService.createPlan(plan));
        verify(planRepository, never()).save(any());
    }
}