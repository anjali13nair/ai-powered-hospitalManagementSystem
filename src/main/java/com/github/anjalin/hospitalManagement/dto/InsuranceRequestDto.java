package com.github.anjalin.hospitalManagement.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class InsuranceRequestDto {
    @NotBlank(message = "Policy number is required")
    private String policyNumber;

    @NotBlank(message = "Provider is required")
    private String provider;

    @FutureOrPresent(message = "Insurance validity date cannot be in the past")
    @NotNull(message = "Insurance validity date is required")
    private LocalDate validUntil;
}
