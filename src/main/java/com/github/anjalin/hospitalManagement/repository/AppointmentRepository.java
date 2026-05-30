package com.github.anjalin.hospitalManagement.repository;

import com.github.anjalin.hospitalManagement.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
}