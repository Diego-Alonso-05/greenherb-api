package com.greenherb.greenherb_api.service;

import com.greenherb.greenherb_api.exception.NotFoundException;
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

        if (plan.getMinHumidity() >= plan.getMaxHumidity()) {
            throw new RuntimeException("minHumidity must be lower than maxHumidity");
        }

        plan.setId(null);

        return planRepository.save(plan);
    }

    public List<Plan> getPlans() {

        return planRepository.findAll();
    }

    public Plan getPlanById(Long id) {

        return planRepository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException("Plan not found")
                );
    }

    public Plan updatePlan(Long id, Plan updatedPlan) {

        if (updatedPlan.getMinTemp() >= updatedPlan.getMaxTemp()) {
            throw new RuntimeException("minTemp must be lower than maxTemp");
        }

        if (updatedPlan.getMinHumidity() >= updatedPlan.getMaxHumidity()) {
            throw new RuntimeException("minHumidity must be lower than maxHumidity");
        }

        Plan existingPlan = getPlanById(id);

        existingPlan.setType(updatedPlan.getType());
        existingPlan.setMinTemp(updatedPlan.getMinTemp());
        existingPlan.setMaxTemp(updatedPlan.getMaxTemp());
        existingPlan.setMinHumidity(updatedPlan.getMinHumidity());
        existingPlan.setMaxHumidity(updatedPlan.getMaxHumidity());
        existingPlan.setActive(updatedPlan.isActive());
        existingPlan.setAutomaticIrrigation(updatedPlan.isAutomaticIrrigation());

        return planRepository.save(existingPlan);
    }

    public void deletePlan(Long id) {

        planRepository.deleteById(id);
    }
}