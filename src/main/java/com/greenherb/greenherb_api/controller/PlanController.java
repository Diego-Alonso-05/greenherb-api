package com.greenherb.greenherb_api.controller;

import com.greenherb.greenherb_api.model.Plan;
import com.greenherb.greenherb_api.service.PlanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/plans")
@RequiredArgsConstructor
public class PlanController {

    private final PlanService planService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Plan createPlan(@Valid @RequestBody Plan plan) {

        return planService.createPlan(plan);
    }

    @GetMapping
    public List<Plan> getPlans() {

        return planService.getPlans();
    }

    @GetMapping("/{id}")
    public Plan getPlanById(@PathVariable Long id) {

        return planService.getPlanById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Plan updatePlan(
            @PathVariable Long id,
            @Valid @RequestBody Plan plan
    ) {

        return planService.updatePlan(id, plan);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deletePlan(@PathVariable Long id) {

        planService.deletePlan(id);
    }
}