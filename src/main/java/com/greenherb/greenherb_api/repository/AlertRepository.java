package com.greenherb.greenherb_api.repository;

import com.greenherb.greenherb_api.model.Alert;
import com.greenherb.greenherb_api.model.AlertStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AlertRepository extends JpaRepository<Alert, Long> {

    Optional<Alert> findByMessageAndLevelAndStatus(
            String message,
            String level,
            AlertStatus status
    );
}