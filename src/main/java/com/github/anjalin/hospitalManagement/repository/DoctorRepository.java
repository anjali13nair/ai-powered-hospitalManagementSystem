package com.github.anjalin.hospitalManagement.repository;

import com.github.anjalin.hospitalManagement.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
}