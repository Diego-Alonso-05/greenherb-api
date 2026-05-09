package com.greenherb.greenherb_api.controller;

import com.greenherb.greenherb_api.model.Plan;
import com.greenherb.greenherb_api.service.PlanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/plans")
@RequiredArgsConstructor
public class PlanController {

    private final PlanService planService;

    @PostMapping
    public Plan createPlan(@Valid @RequestBody Plan plan) {
        return planService.createPlan(plan);
    }

    @GetMapping
    public List<Plan> getPlans() {
        return planService.getPlans();
    }
}