package com.greenherb.greenherb_api.repository;

import com.greenherb.greenherb_api.model.Alert;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlertRepository extends JpaRepository<Alert, Long> {
}