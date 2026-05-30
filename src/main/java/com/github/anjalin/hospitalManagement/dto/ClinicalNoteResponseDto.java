package com.github.anjalin.hospitalManagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClinicalNoteResponseDto {
    private Long appointmentId;
    private String symptoms;
    private String prescription;
    private String summary;
}
