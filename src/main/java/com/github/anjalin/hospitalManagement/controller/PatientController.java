package com.github.anjalin.hospitalManagement.controller;

import com.github.anjalin.hospitalManagement.dto.AppointmentResponseDto;
import com.github.anjalin.hospitalManagement.dto.CreateAppointmentRequestDto;
import com.github.anjalin.hospitalManagement.dto.PatientResponseDto;
import com.github.anjalin.hospitalManagement.service.AppointmentService;
import com.github.anjalin.hospitalManagement.service.PatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;
    private final AppointmentService appointmentService;

    @PostMapping("/appointments")
    public ResponseEntity<AppointmentResponseDto> createNewAppointment(@Valid @RequestBody CreateAppointmentRequestDto createAppointmentRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(appointmentService.createNewAppointment(createAppointmentRequestDto));
    }

    @GetMapping("/{patientId}/profile")
    public ResponseEntity<PatientResponseDto> getPatientProfile(@PathVariable Long patientId) {
        return ResponseEntity.ok(patientService.getPatientById(patientId));
    }

    @GetMapping("/all")
    public ResponseEntity<List<PatientResponseDto>> getAllPatients(@RequestParam Integer pageNumber, @RequestParam Integer pageSize) {
        return ResponseEntity.ok(patientService.getAllPatients(pageNumber,pageSize));
    }

    @GetMapping("/{name}")
    public ResponseEntity<List<PatientResponseDto>> getPatientByName(@PathVariable String name) {
        return ResponseEntity.ok(patientService.getPatientsByName(name));
    }

//    @DeleteMapping("/{patientId}")
//    public ResponseEntity<Void> deletePatient(@PathVariable Long patientId) {
//        patientService.deletePatient(patientId);
//        return ResponseEntity.noContent().build(); // Returns a standard 204 No Content status on success
//    }

}
