package com.github.anjalin.hospitalManagement.controller;

import com.github.anjalin.hospitalManagement.dto.InsuranceRequestDto;
import com.github.anjalin.hospitalManagement.dto.PatientResponseDto;
import com.github.anjalin.hospitalManagement.service.InsuranceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/insurance")
@RequiredArgsConstructor
public class InsuranceController {

    private final InsuranceService insuranceService;

    @PostMapping("/patient/{patientId}")
    @PreAuthorize("hasRole('ADMIN') or #patientId == authentication.principal.id")
    public ResponseEntity<PatientResponseDto> assignInsurance(
            @PathVariable Long patientId,
            @Valid @RequestBody InsuranceRequestDto insuranceRequestDto) {
        return ResponseEntity.ok(insuranceService.assignInsuranceToPatient(insuranceRequestDto, patientId));
    }

    @DeleteMapping("/patient/{patientId}")
    @PreAuthorize("hasRole('ADMIN') or #patientId == authentication.principal.id")
    public ResponseEntity<PatientResponseDto> removeInsurance(@PathVariable Long patientId) {
        return ResponseEntity.ok(insuranceService.disaccociateInsuranceFromPatient(patientId));
    }
}
