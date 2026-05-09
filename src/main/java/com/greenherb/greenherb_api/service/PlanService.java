package com.greenherb.greenherb_api.service;

import com.greenherb.greenherb_api.model.Plan;
import com.greenherb.greenherb_api.repository.PlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlanService {

    private final PlanRepository planRepository;

    public Plan createPlan(Plan plan) {

        if (plan.getMinTemp() >= plan.getMaxTemp()) {
            throw new RuntimeException("minTemp must be lower than maxTemp");
        }

        return planRepository.save(plan);
    }

    public List<Plan> getPlans() {
        return planRepository.findAll();
    }
}