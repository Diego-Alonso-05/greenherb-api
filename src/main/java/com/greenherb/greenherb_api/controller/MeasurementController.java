package com.greenherb.greenherb_api.controller;

import com.greenherb.greenherb_api.model.Measurement;
import com.greenherb.greenherb_api.service.MeasurementService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/measurements")
@RequiredArgsConstructor
public class MeasurementController {

    private final MeasurementService measurementService;

    @PostMapping
    @PreAuthorize("hasAnyRole('TECHNICIAN', 'ADMIN')")
    public Measurement createMeasurement(
            @RequestBody Measurement measurement
    ) {

        return measurementService.saveMeasurement(measurement);
    }
}