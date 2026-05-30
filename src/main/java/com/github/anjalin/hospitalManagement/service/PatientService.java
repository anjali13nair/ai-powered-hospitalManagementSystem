package com.github.anjalin.hospitalManagement.service;

import com.github.anjalin.hospitalManagement.dto.PatientResponseDto;
import com.github.anjalin.hospitalManagement.entity.Patient;
import com.github.anjalin.hospitalManagement.repository.PatientRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;
    private final ModelMapper modelMapper;

    /**
     * It uses the repository to find a patient. If the ID doesn't exist,
     * it uses .orElseThrow() to immediately trigger an EntityNotFoundException.
     * This stops the code from failing silently.
     * If found, it maps the raw entity to a PatientResponseDto before returning it.
     * @param patientId
     * @return
     */
    @Transactional
    public PatientResponseDto getPatientById(Long patientId) {
        Patient patient = patientRepository.findById(patientId).orElseThrow(() -> new EntityNotFoundException("Patient Not " +
                "Found with id: " + patientId));
        return modelMapper.map(patient, PatientResponseDto.class);
    }

    /**
     * Instead of fetching all patients at once, it utilizes Spring's PageRequest to
     * fetch a specific "slice" of data based on the provided pageNumber and pageSize.
     * It then uses a Java Stream to map every entity in that page into a DTO.
     * @param pageNumber
     * @param pageSize
     * @return
     */
    public List<PatientResponseDto> getAllPatients(Integer pageNumber, Integer pageSize) {
        return patientRepository.findAllPatients(PageRequest.of(pageNumber, pageSize))
                .stream()
                .map(patient -> modelMapper.map(patient, PatientResponseDto.class))
                .collect(Collectors.toList());
    }

    //It verifies the patient exists using existsById()
    // before attempting deletion to avoid database errors.
    @Transactional
    public void deletePatient(Long id) {
        if (!patientRepository.existsById(id)) {
            throw new EntityNotFoundException("Patient not found");
        }
        patientRepository.deleteById(id);
    }

    public List<PatientResponseDto> getPatientsByName(String name) {
        // 1. Fetch the raw entities from the database
        List<Patient> patients = patientRepository.findByName(name);
        // 2. Convert the List of Entities into a List of DTOs using Java Streams
        return patients.stream()
                .map(patient -> modelMapper.map(patient, PatientResponseDto.class))
                .collect(Collectors.toList());
    }
}
