package com.greenherb.greenherb_api.repository;

import com.greenherb.greenherb_api.model.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeasurementRepository extends JpaRepository<Measurement, Long> {
}