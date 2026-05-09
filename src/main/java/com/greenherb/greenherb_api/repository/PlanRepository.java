package com.greenherb.greenherb_api.repository;

import com.greenherb.greenherb_api.model.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlanRepository extends JpaRepository<Plan, Long> {

    Optional<Plan> findByActiveTrue();
}