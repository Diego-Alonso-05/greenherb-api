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

    @NotBlank
    private String type;

    @Min(10)
    private double minTemp;

    @Max(50)
    private double maxTemp;

    @Min(0)
    @Max(100)
    private double minHumidity;

    @Min(0)
    @Max(100)
    private double maxHumidity;

    private boolean active;

    private boolean automaticIrrigation;
}