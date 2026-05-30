package com.github.anjalin.hospitalManagement.repository;

import com.github.anjalin.hospitalManagement.entity.Insurance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InsuranceRepository extends JpaRepository<Insurance, Long> {
}