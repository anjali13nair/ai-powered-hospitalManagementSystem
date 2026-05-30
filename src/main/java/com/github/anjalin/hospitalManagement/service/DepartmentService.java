package com.github.anjalin.hospitalManagement.service;

import com.github.anjalin.hospitalManagement.dto.DepartmentRequestDto;
import com.github.anjalin.hospitalManagement.dto.DepartmentResponseDto;
import com.github.anjalin.hospitalManagement.entity.Department;
import com.github.anjalin.hospitalManagement.entity.Doctor;
import com.github.anjalin.hospitalManagement.repository.DepartmentRepository;
import com.github.anjalin.hospitalManagement.repository.DoctorRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final DoctorRepository doctorRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public DepartmentResponseDto createDepartment(DepartmentRequestDto requestDto) {
        Department department = new Department();
        department.setName(requestDto.getName());

        if (requestDto.getHeadDoctorId() != null) {
            Doctor headDoctor = doctorRepository.findById(requestDto.getHeadDoctorId())
                    .orElseThrow(() -> new EntityNotFoundException("Doctor not found"));
            department.setHeadDoctor(headDoctor);
            department.getDoctors().add(headDoctor);
        }

        return modelMapper.map(departmentRepository.save(department), DepartmentResponseDto.class);
    }

    public List<DepartmentResponseDto> getAllDepartments() {
        return departmentRepository.findAll().stream()
                .map(dept -> modelMapper.map(dept, DepartmentResponseDto.class))
                .collect(Collectors.toList());
    }
}