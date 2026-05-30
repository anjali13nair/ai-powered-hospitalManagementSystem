package com.github.anjalin.hospitalManagement.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime appointmentTime;

    @Column(length = 500)
    private String reason;

    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "patient_id", nullable = false) // patient is required and not nullable
    private Patient patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    @JoinColumn(nullable = false)
    private Doctor doctor;

    // Inside your Appointment.java entity

    @Column(length = 1000)
    private String symptoms;

    @Column(length = 1000)
    private String prescription;

    @Column(length = 1000)
    private String summary;
}
