package com.github.anjalin.hospitalManagement.controller;

import com.github.anjalin.hospitalManagement.dto.ClinicalNoteResponseDto;
import com.github.anjalin.hospitalManagement.dto.TriageRequestDto;
import com.github.anjalin.hospitalManagement.dto.TriageResponseDto;
import com.github.anjalin.hospitalManagement.entity.Appointment;
import com.github.anjalin.hospitalManagement.service.AiTriageService;
import com.github.anjalin.hospitalManagement.service.ClinicalNoteExtractorService;
import com.github.anjalin.hospitalManagement.service.PatientHistoryRAGService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/ai")
@RequiredArgsConstructor
public class AiController {

    private final AiTriageService aiTriageService;
    private final PatientHistoryRAGService patientHistoryRAGService;

    private final ClinicalNoteExtractorService clinicalNoteExtractorService;

    @PostMapping("/triage")
    @Secured("ROLE_PATIENT")
    public ResponseEntity<TriageResponseDto> getTriageRecommendation(@Valid @RequestBody TriageRequestDto requestDto) {
        TriageResponseDto recommendation = aiTriageService.analyzeSymptoms(requestDto.getSymptoms());
        return ResponseEntity.ok(recommendation);
    }



    @GetMapping("/patient/{patientId}/ask")
    @Secured({"ROLE_DOCTOR", "ROLE_ADMIN"})
    public ResponseEntity<String> askPatientHistory(
            @PathVariable Long patientId,
            @RequestParam String question) {

        String answer = patientHistoryRAGService.askAboutPatientHistory(patientId, question);
        return ResponseEntity.ok(answer);
    }

    /**
     * Extracts structured clinical data from a raw note and saves it to the appointment.
     * SECURED: Only users with the DOCTOR role can access this endpoint.
     */
    @PostMapping("/notes/extract/{appointmentId}")
    @Secured("ROLE_DOCTOR") // <-- The crucial security gate!
    public ResponseEntity<ClinicalNoteResponseDto> extractAndSaveClinicalNote(
            @PathVariable Long appointmentId,
            @RequestBody String rawDoctorNote) {

        // 1. Pass the ID and the raw text to the Service layer
        Appointment updatedAppointment = clinicalNoteExtractorService.extractAndSaveNotes(appointmentId, rawDoctorNote);

        ClinicalNoteResponseDto responseDto = new ClinicalNoteResponseDto(
                updatedAppointment.getId(),
                updatedAppointment.getSymptoms(),
                updatedAppointment.getPrescription(),
                updatedAppointment.getSummary()
        );

        return ResponseEntity.ok(responseDto);
    }
}
