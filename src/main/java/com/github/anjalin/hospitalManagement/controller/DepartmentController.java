package com.github.anjalin.hospitalManagement.controller;

import com.github.anjalin.hospitalManagement.dto.DepartmentRequestDto;
import com.github.anjalin.hospitalManagement.dto.DepartmentResponseDto;
import com.github.anjalin.hospitalManagement.service.DepartmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/departments")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    @GetMapping("/public")
    public ResponseEntity<List<DepartmentResponseDto>> getAllDepartments() {
        return ResponseEntity.ok(departmentService.getAllDepartments());
    }

    @PostMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DepartmentResponseDto> createDepartment(@Valid @RequestBody DepartmentRequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(departmentService.createDepartment(requestDto));
    }
}
