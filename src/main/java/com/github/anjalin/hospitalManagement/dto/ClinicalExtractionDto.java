package com.github.anjalin.hospitalManagement.dto;

import lombok.Data;
import java.util.List;

@Data
public class ClinicalExtractionDto {
    private String primaryDiagnosis;
    private List<String> prescribedMedications;
    private Integer followUpInDays;
    private String recommendedLifestyleChanges;
}