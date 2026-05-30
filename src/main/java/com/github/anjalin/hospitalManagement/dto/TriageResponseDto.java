package com.github.anjalin.hospitalManagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TriageResponseDto {
    private String recommendedDepartment;
    private String urgencyLevel; // e.g., LOW, MEDIUM, HIGH, CRITICAL
    private String generalAdvice;
}