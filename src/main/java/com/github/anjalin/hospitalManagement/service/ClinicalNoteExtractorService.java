package com.github.anjalin.hospitalManagement.service;

import com.github.anjalin.hospitalManagement.entity.Appointment;
import com.github.anjalin.hospitalManagement.repository.AppointmentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.stereotype.Service;

@Service
public class ClinicalNoteExtractorService {

    // 1. The Dependencies
    private final ChatClient chatClient;
    private final AppointmentRepository appointmentRepository;
    private final PatientHistoryRAGService patientHistoryRAGService;

    // 2. The Fixed Constructor (Inject the Builder, not the Client!)
    public ClinicalNoteExtractorService(ChatClient.Builder chatClientBuilder,
                                        AppointmentRepository appointmentRepository,
                                        PatientHistoryRAGService patientHistoryRAGService) {
        this.chatClient = chatClientBuilder.build();
        this.appointmentRepository = appointmentRepository;
        this.patientHistoryRAGService = patientHistoryRAGService;
    }

    // 3. The exact Java structure we want the AI to return
    public record ExtractedClinicalData(String symptoms, String prescription, String summary) {}

    // 4. The Main Method
    public Appointment extractAndSaveNotes(Long appointmentId, String rawDoctorNote) {

        // A. Setup the AI formatting rules
        var outputConverter = new BeanOutputConverter<>(ExtractedClinicalData.class);
        String formatInstructions = outputConverter.getFormat();

        // B. Call OpenAI
        String aiResponse = chatClient.prompt()
                .user(u -> u.text("You are a medical assistant. Extract the clinical data from this raw note: {note}. {format}")
                        .param("note", rawDoctorNote)
                        .param("format", formatInstructions))
                .call()
                .content();

        // C. Convert the AI's string response back into a Java Record
        ExtractedClinicalData structuredData = outputConverter.convert(aiResponse);

        // D. Fetch, Update, and Save to the Database!
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new EntityNotFoundException("Appointment not found with ID: " + appointmentId));

        appointment.setSymptoms(structuredData.symptoms());
        appointment.setPrescription(structuredData.prescription());
        appointment.setSummary(structuredData.summary());

        Appointment savedAppointment = appointmentRepository.save(appointment);

        patientHistoryRAGService.ingestAppointmentNote(
                savedAppointment.getPatient().getId(),
                buildClinicalNoteText(savedAppointment)
        );

        return savedAppointment;
    }

    private String buildClinicalNoteText(Appointment appointment) {
        return "Symptoms: " + appointment.getSymptoms() + "\n"
                + "Prescription: " + appointment.getPrescription() + "\n"
                + "Summary: " + appointment.getSummary();
    }
}
