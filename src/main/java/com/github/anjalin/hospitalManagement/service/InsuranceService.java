package com.github.anjalin.hospitalManagement.service;

import com.github.anjalin.hospitalManagement.dto.InsuranceRequestDto;
import com.github.anjalin.hospitalManagement.dto.PatientResponseDto;
import com.github.anjalin.hospitalManagement.entity.Insurance;
import com.github.anjalin.hospitalManagement.entity.Patient;
import com.github.anjalin.hospitalManagement.repository.InsuranceRepository;
import com.github.anjalin.hospitalManagement.repository.PatientRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InsuranceService {

    private final InsuranceRepository insuranceRepository;
    private final PatientRepository patientRepository;
    private final ModelMapper modelMapper; // Injected ModelMapper

    @Transactional
    public PatientResponseDto assignInsuranceToPatient(InsuranceRequestDto insuranceRequestDto, Long patientId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new EntityNotFoundException("Patient not found with id: " + patientId));

        // Map the incoming DTO to the Insurance Entity
        Insurance insurance = modelMapper.map(insuranceRequestDto, Insurance.class);

        patient.setInsurance(insurance);
        insurance.setPatient(patient); // bidirectional consistency maintenance

        // Return the updated patient mapped as a DTO
        return modelMapper.map(patient, PatientResponseDto.class);
    }

    @Transactional
    public PatientResponseDto disaccociateInsuranceFromPatient(Long patientId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new EntityNotFoundException("Patient not found with id: " + patientId));

        patient.setInsurance(null);

        // Return the updated patient mapped as a DTO (insurance will be null)
        return modelMapper.map(patient, PatientResponseDto.class);
    }

    // HW
    //Create three appointment for a patient and then delete Patient
}