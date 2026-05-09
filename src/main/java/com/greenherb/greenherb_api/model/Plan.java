package com.greenherb.greenherb_api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Plan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Type cannot be empty")
    private String type;

    @Min(value = 10, message = "Minimum temperature must be at least 10")
    @Max(value = 50, message = "Minimum temperature cannot exceed 50")
    private double minTemp;

    @Min(value = 10, message = "Maximum temperature must be at least 10")
    @Max(value = 50, message = "Maximum temperature cannot exceed 50")
    private double maxTemp;

    @Min(value = 0, message = "Minimum humidity cannot be below 0")
    @Max(value = 100, message = "Minimum humidity cannot exceed 100")
    private double minHumidity;

    @Min(value = 0, message = "Maximum humidity cannot be below 0")
    @Max(value = 100, message = "Maximum humidity cannot exceed 100")
    private double maxHumidity;

    private boolean active;

    private boolean automaticIrrigation;
}