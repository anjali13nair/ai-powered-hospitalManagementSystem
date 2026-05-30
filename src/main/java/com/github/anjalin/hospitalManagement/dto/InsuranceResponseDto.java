package com.github.anjalin.hospitalManagement.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class InsuranceResponseDto {
    private Long id;
    private String policyNumber;
    private String provider;
    private LocalDate validUntil;
    private LocalDateTime createdAt;
}