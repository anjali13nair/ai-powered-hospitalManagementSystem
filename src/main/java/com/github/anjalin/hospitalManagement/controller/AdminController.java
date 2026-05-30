package com.github.anjalin.hospitalManagement.controller;

import com.github.anjalin.hospitalManagement.dto.DoctorResponseDto;
import com.github.anjalin.hospitalManagement.dto.OnboardDoctorRequestDto;
import com.github.anjalin.hospitalManagement.dto.PatientResponseDto;
import com.github.anjalin.hospitalManagement.service.DoctorService;
import com.github.anjalin.hospitalManagement.service.PatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final PatientService patientService;
    private final DoctorService doctorService;

    @GetMapping("/patients")
    public ResponseEntity<List<PatientResponseDto>> getAllPatients(
            @RequestParam(value = "page", defaultValue = "0") Integer pageNumber,
            @RequestParam(value = "size", defaultValue = "10") Integer pageSize
    ) {
        //User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(patientService.getAllPatients(pageNumber, pageSize));
    }

    @PostMapping("/onBoardNewDoctor")
    public ResponseEntity<DoctorResponseDto> onBoardNewDoctor(@Valid @RequestBody OnboardDoctorRequestDto onboardDoctorRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(doctorService.onBoardNewDoctor(onboardDoctorRequestDto));
    }

    @DeleteMapping("/patients/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id); // You will need to add this to PatientService
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/doctors/{id}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable Long id) {
        doctorService.deleteDoctor(id); // You will need to add this to DoctorService
        return ResponseEntity.noContent().build();
    }
}
