package com.github.anjalin.hospitalManagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OnboardDoctorRequestDto {
    @NotNull(message = "User id is required")
    private Long userId;

    @NotBlank(message = "Specialization is required")
    private String specialization;

    @NotBlank(message = "Doctor name is required")
    private String name;
}
