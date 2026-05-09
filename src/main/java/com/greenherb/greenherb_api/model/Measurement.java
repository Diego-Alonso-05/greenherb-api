package com.greenherb.greenherb_api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Measurement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Min(-20)
    @Max(80)
    private double temperature;

    @Min(0)
    @Max(100)
    private double humidity;

    private LocalDateTime createdAt;
}