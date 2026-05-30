package com.github.anjalin.hospitalManagement.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TriageRequestDto {
    @NotBlank(message = "Symptoms are required")
    private String symptoms;
}


