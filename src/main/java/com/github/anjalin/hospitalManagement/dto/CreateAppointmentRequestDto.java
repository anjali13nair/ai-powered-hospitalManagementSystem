package com.github.anjalin.hospitalManagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateAppointmentRequestDto {
    @NotNull(message = "Doctor id is required")
    private Long doctorId;
    private Long patientId;

    @NotNull(message = "Appointment time is required")
    private LocalDateTime appointmentTime;

    @NotBlank(message = "Reason is required")
    private String reason;
}
