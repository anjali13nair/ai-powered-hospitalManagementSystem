package com.github.anjalin.hospitalManagement.dto;

import com.github.anjalin.hospitalManagement.entity.type.BloodGroupType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PatientResponseDto {
    private Long id;
    private String name;
    private String gender;
    private LocalDate birthDate;
    private BloodGroupType bloodGroup;

    // Added InsuranceResponseDto to map the nested insurance details
    private InsuranceResponseDto insurance;
}